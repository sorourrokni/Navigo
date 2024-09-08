package com.example.navigo.data.network

import com.example.navigo.data.model.request.MapMatchingRequest
import com.example.navigo.data.model.response.MapMatchingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MapMatchingService {
    @POST("v3/map-matching")
    fun matchPath(
        @Body requestBody: MapMatchingRequest
    ): Call<MapMatchingResponse>
}
