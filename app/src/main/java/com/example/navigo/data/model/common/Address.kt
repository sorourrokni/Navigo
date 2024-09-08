package com.example.navigo.data.model.common

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("status")
    var status: String?,

    @SerializedName("formatted_address")
    var address: String?,

    @SerializedName("route_name")
    var routeName: String?,

    @SerializedName("route_type")
    var routeType: String?,

    @SerializedName("neighbourhood")
    var neighbourhood: String?,

    @SerializedName("city")
    var city: String?,

    @SerializedName("state")
    var state: String?,

    @SerializedName("place")
    var place: String?,

    @SerializedName("municipality_zone")
    var municipalityZone: String?,

    @SerializedName("in_traffic_zone")
    var inTrafficZone: Boolean?,

    @SerializedName("in_odd_even_zone")
    var inOddEvenZone: Boolean?,

    @SerializedName("village")
    var village: String?,

    @SerializedName("county")
    var county: String?,

    @SerializedName("district")
    var district: String?
)
