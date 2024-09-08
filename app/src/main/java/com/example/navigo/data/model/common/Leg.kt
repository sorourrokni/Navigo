package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Leg(
    @SerializedName("summary") val summary: String?,
    @SerializedName("distance") val distance: Distance,
    @SerializedName("duration") val duration: Duration,
    @SerializedName("steps") val steps: List<Step>
)
