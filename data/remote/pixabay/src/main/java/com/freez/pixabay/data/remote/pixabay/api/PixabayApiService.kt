package com.freez.pixabay.data.remote.pixabay.api

import com.freez.pixabay.data.remote.pixabay.models.SearchVideoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PixabayApiService {

    @Headers("Content-Type: application/json")
    @GET("videos/")
    suspend fun searchVideo(
        @Query("q") query: String? = null,
        @Query("lang") language: String? = null,
        @Query("id") videoId: String? = null,
        @Query("video_type") videoType: String? = null,
        @Query("category") category: String? = null,
        @Query("min_width") minWidth: Int? = null,
        @Query("min_height") minHeight: Int? = null,
        @Query("editors_choice") editorsChoice: Boolean? = null,
        @Query("safesearch") safeSearch: Boolean? = null,
        @Query("order") order: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("callback") callback: String? = null,
        @Query("pretty") pretty: Boolean? = null,
    ): SearchVideoResponse
}
