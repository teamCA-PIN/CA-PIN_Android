package com.caffeine.capin.map.dto

data class MyMapLocationDTO(
    val cafes: List<CafeLocationDTO>,
    val color: String,
    val name: String
)