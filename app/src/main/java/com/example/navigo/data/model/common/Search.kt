package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("title")
    val title: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("neighbourhood")
    val neighbourhood: String?,

    @SerializedName("region")
    val region: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("category")
    val category: String,

    @SerializedName("location")
    val location: Location
)