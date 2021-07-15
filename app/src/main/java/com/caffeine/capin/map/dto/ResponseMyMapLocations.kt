package com.caffeine.capin.map.dto

data class ResponseMyMapLocations(
    val message: String,
    val myMapLocationDTOS: List<MyMapLocationDTO>
)