package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("name")
    val name: String,

    @SerializedName("location")
    val location: List<Double>,

    @SerializedName("index")
    val index: Int
)