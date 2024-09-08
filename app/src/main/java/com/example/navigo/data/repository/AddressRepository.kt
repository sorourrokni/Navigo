package com.example.navigo.data.repository

import com.example.navigo.data.model.common.Address
import com.example.navigo.data.network.ApiService.reverseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressRepository {

    fun getAddress(lat: Double, lng: Double, callback: (Address?) -> Unit) {
        val call = reverseService.getReverse(lat, lng)
        call.enqueue(object : Callback<Address> {
            override fun onResponse(call: Call<Address>, response: Response<Address>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Address>, t: Throwable) {
                callback(null)
            }
        })
    }
}
