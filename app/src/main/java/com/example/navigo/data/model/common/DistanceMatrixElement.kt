package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName
data class DistanceMatrixElement(
    @SerializedName("status")
    val status: String,

    @SerializedName("duration")
    val duration: Duration,

    @SerializedName("distance")
    val distance: Distance
)