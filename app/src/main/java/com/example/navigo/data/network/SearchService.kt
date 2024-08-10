package com.example.navigo.data.network

import com.example.navigo.data.model.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchService {
    @GET("v1/search")
    fun search(
        @Query("term") term: String,
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
    ): Call<SearchResponse>
}
