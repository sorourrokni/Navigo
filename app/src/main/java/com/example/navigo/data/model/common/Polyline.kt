package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("points")
    val points: String
)