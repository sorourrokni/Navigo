package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class DistanceMatrixRow(
    @SerializedName("elements")
    val elements: List<DistanceMatrixElement>
)
