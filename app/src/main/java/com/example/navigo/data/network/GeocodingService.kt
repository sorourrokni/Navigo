package com.example.navigo.data.network

import com.example.navigo.data.model.response.GeocodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("v4/geocoding")
    fun getCoordinates(
        @Query("address") address: String
    ): Call<GeocodingResponse>
}
