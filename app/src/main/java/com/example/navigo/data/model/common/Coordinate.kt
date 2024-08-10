package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)
