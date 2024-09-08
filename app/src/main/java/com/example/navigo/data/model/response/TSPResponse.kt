package com.example.navigo.data.model.response

import com.example.navigo.data.model.common.Point
import com.google.gson.annotations.SerializedName

data class TSPResponse(
    @SerializedName("points")
    val points: List<Point>
)