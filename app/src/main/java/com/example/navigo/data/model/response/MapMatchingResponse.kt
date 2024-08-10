package com.example.navigo.data.model.response

import com.example.navigo.data.model.common.SnappedPoint
import com.google.gson.annotations.SerializedName

data class MapMatchingResponse(
    @SerializedName("snappedPoints")
    val snappedPoints: List<SnappedPoint>,

    @SerializedName("geometry")
    val geometry: String
)
