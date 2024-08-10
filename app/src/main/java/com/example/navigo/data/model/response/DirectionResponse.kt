package com.example.navigo.data.model.response

import com.example.navigo.data.model.common.Route
import com.google.gson.annotations.SerializedName

data class DirectionResponse(
    @SerializedName("routes")
    val routes: List<Route>
)