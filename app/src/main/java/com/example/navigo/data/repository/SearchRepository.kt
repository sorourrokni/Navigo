package com.example.navigo.data.repository

import com.example.navigo.data.model.response.SearchResponse
import com.example.navigo.data.network.ApiService.searchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    fun search(
        term: String,
        latitude: Double,
        longitude: Double,
        callback: (SearchResponse?) -> Unit
    ) {
        searchService.search(term, latitude, longitude).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}