package com.example.navigo.data.model.response

import com.example.navigo.data.model.common.Location
import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("location")
    val location: Location
)