package com.example.navigo.data.network

import com.example.navigo.data.model.response.TSPResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TSPService {
    @GET("v3/trip")
    fun getOptimalRoute(
        @Query("waypoints") waypoints: String,
        @Query("roundTrip") roundTrip: Boolean = true,
        @Query("sourceIsAnyPoint") sourceIsAnyPoint: Boolean = true,
        @Query("lastIsAnyPoint") lastIsAnyPoint: Boolean = true
    ): Call<TSPResponse>
}
