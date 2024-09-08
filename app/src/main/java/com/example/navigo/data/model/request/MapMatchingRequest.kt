package com.example.navigo.data.model.request

import com.google.gson.annotations.SerializedName

data class MapMatchingRequest(
    @SerializedName("path")
    val path: String
)
