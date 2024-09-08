package com.example.navigo.data.repository

import com.example.navigo.data.model.response.DirectionResponse
import com.example.navigo.data.model.response.TSPResponse
import com.example.navigo.data.network.ApiService.directionService
import com.example.navigo.data.network.ApiService.tspService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectionRepository {

    fun getRouteWithTraffic(
        origin: String,
        destination: String,
        callback: (DirectionResponse?) -> Unit
    ) {
        val call = directionService.getRouteWithTraffic(origin = origin, destination = destination)
        call.enqueue(object : Callback<DirectionResponse> {
            override fun onResponse(
                call: Call<DirectionResponse>,
                response: Response<DirectionResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DirectionResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getOptimalRoute(
        waypoints: String,
        onSuccess: (TSPResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        tspService.getOptimalRoute(waypoints).enqueue(object : Callback<TSPResponse> {
            override fun onResponse(call: Call<TSPResponse>, response: Response<TSPResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onFailure(Throwable("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<TSPResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}