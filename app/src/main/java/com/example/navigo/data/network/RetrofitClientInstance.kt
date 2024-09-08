package com.example.navigo.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    private const val BASE_URL = "https://api.neshan.org/"
    private const val API_KEY = "service.18188f2871a44362b006c89e0236c7a5"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(API_KEY))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val retrofitInstance: Retrofit
        get() = retrofit
}
