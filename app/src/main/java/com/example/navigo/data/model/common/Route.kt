package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("overview_polyline")
    val overviewPolyline: Polyline,

    @SerializedName("legs")
    val legs: List<Leg>
)
