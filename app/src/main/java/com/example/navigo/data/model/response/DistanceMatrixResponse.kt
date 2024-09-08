package com.example.navigo.data.model.response

import com.example.navigo.data.model.common.DistanceMatrixRow
import com.google.gson.annotations.SerializedName

data class DistanceMatrixResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("rows")
    val rows: List<DistanceMatrixRow>,

    @SerializedName("origin_addresses")
    val originAddresses: List<String>,

    @SerializedName("destination_addresses")
    val destinationAddresses: List<String>
)