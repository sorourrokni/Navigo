package com.example.navigo.data.model.common

import com.example.navigo.data.model.common.Distance
import com.example.navigo.data.model.common.Duration
import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("name") val name: String?,
    @SerializedName("instruction") val instruction: String?,
    @SerializedName("bearing_after") val bearingAfter: Int,
    @SerializedName("type") val type: String,
    @SerializedName("modifier") val modifier: String,
    @SerializedName("distance") val distance: Distance,
    @SerializedName("duration") val duration: Duration,
    @SerializedName("polyline") val polyline: String,
    @SerializedName("start_location") val startLocation: List<Double>
)