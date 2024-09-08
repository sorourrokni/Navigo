package com.example.navigo.data.network

import com.example.navigo.data.model.common.Address
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReverseService {
    @GET("/v5/reverse")
    fun getReverse(@Query("lat") lat: Double?, @Query("lng") lng: Double?): Call<Address>
}