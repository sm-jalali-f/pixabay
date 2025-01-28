package com.freez.pixabay.data.remote.pixabay.api

import com.freez.pixabay.data.remote.pixabay.models.SearchVideoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PixabayApiService {

    @Headers("Content-Type: application/json")
    @GET("videos/")
    suspend fun searchVideo(
        @Query("q") query: String? = null,
        @Query("lang") language: String = "en",
        @Query("id") videoId: String? = null,
        @Query("video_type") videoType: String = "all",
        @Query("category") category: String? = null,
        @Query("min_width") minWidth: Int = 0,
        @Query("min_height") minHeight: Int = 0,
        @Query("editors_choice") editorsChoice: Boolean = false,
        @Query("safesearch") safeSearch: Boolean = false,
        @Query("order") order: String = "popular",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("callback") callback: String? = null,
        @Query("pretty") pretty: Boolean = false
    ): Call<SearchVideoResponse>
}