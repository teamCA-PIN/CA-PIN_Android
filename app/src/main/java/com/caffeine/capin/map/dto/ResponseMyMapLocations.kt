package com.caffeine.capin.map.dto

import com.google.gson.annotations.SerializedName

data class ResponseMyMapLocations(
    val message: String,
    @SerializedName("myMapLocations")
    val myMapLocationDTO: List<MyMapLocationDTO>
)