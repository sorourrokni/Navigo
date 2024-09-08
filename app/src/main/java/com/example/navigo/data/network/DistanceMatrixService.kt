package com.example.navigo.data.network

import com.example.navigo.data.model.response.DistanceMatrixResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DistanceMatrixService {
    @GET("v1/distance-matrix")
    fun getDistanceMatrixWithTraffic(
        @Query("type") type: String = "car",
        @Query("origins") origins: String,
        @Query("destinations") destinations: String
    ): Call<DistanceMatrixResponse>

    @GET("v1/distance-matrix/no-traffic")
    fun getDistanceMatrixWithoutTraffic(
        @Query("type") type: String = "car",
        @Query("origins") origins: String,
        @Query("destinations") destinations: String
    ): Call<DistanceMatrixResponse>
}
