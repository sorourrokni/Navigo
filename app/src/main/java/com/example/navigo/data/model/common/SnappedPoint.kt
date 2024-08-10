package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class SnappedPoint(
    @SerializedName("location")
    val location: Coordinate,

    @SerializedName("originalIndex")
    val originalIndex: Int
)