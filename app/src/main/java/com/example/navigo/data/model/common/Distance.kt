package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Distance(
    @SerializedName("value") val value: Double,
    @SerializedName("text") val text: String
)
