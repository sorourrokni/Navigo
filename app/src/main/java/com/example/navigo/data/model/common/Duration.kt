package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Duration(
    @SerializedName("value") val value: Double,
    @SerializedName("text") val text: String
)