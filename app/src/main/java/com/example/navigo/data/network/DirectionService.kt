package com.example.navigo.data.network

import com.example.navigo.data.model.response.DirectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionService {
    @GET("v4/direction")
    fun getRouteWithTraffic(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("type") type: String = "car",  // Default type is car
        @Query("waypoints") waypoints: String? = null,
        @Query("avoidTrafficZone") avoidTrafficZone: Boolean? = false,
        @Query("avoidOddEvenZone") avoidOddEvenZone: Boolean? = false,
        @Query("alternative") alternative: Boolean? = null,
        @Query("bearing") bearing: Int? = null
    ): Call<DirectionResponse>

    @GET("v4/direction/no-traffic")
    fun getRouteWithoutTraffic(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("type") type: String = "car",
        @Query("waypoints") waypoints: String? = null,
        @Query("avoidTrafficZone") avoidTrafficZone: Boolean? = false,
        @Query("avoidOddEvenZone") avoidOddEvenZone: Boolean? = false,
        @Query("alternative") alternative: Boolean? = null,
        @Query("bearing") bearing: Int? = null
    ): Call<DirectionResponse>
}
