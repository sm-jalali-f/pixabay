package com.freez.pixabay.data.remote.pixabay

import com.freez.pixabay.data.remote.pixabay.api.PixabayApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class PixabayApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: PixabayApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(PixabayApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getVideos returns success response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK) // 200 OK
            .setBody(
                """
                {
                    "total": 100,
                    "totalHits": 50,
                    "hits": [
                        {
                            "id": 1,
                            "url": "https://example.com/video1",
                            "type": "film"
                        }
                    ]
                }
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val response = apiService.searchVideo("flower")

//        assertEquals(1, response.awaitResponse().body()?.hits?.size)
        val request = mockWebServer.takeRequest()

        assertEquals("/?key=API_KEY&q=flower", request.path)
    }
}