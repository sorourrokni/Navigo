package com.example.navigo.data.repository

import com.example.navigo.data.model.response.DistanceMatrixResponse
import com.example.navigo.data.network.ApiService.distanceMatrixService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistanceRepository {

    fun getDistanceMatrixWithTraffic(
        type: String,
        origins: String,
        destinations: String,
        callback: (DistanceMatrixResponse?) -> Unit
    ) {
        val call = distanceMatrixService.getDistanceMatrixWithTraffic(type, origins, destinations)
        call.enqueue(object : Callback<DistanceMatrixResponse> {
            override fun onResponse(
                call: Call<DistanceMatrixResponse>,
                response: Response<DistanceMatrixResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DistanceMatrixResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getDistanceMatrixWithoutTraffic(
        type: String,
        origins: String,
        destinations: String,
        callback: (DistanceMatrixResponse?) -> Unit
    ) {
        val call =
            distanceMatrixService.getDistanceMatrixWithoutTraffic(type, origins, destinations)
        call.enqueue(object : Callback<DistanceMatrixResponse> {
            override fun onResponse(
                call: Call<DistanceMatrixResponse>,
                response: Response<DistanceMatrixResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DistanceMatrixResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}
