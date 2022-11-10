package com.thoughtctl.network

import com.thoughtctl.model.GaleryArray
import com.thoughtctl.model.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("3/gallery/search/top/top/week/1")
    suspend fun getIssues(): Response<GaleryArray>

    @GET("3/gallery/search/top/top/week/1")
    suspend fun getImages(@Query("q") query: String,@Query("q_type") type: String): Response<ImagesResponse>


}