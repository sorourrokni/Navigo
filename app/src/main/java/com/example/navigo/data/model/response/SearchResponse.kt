package com.example.navigo.data.model.response

import com.example.navigo.data.model.common.Search
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("items")
    val items: List<Search>
)