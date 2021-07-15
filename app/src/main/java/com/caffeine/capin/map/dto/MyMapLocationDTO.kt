package com.caffeine.capin.map.dto

data class MyMapLocationDTO(
    val cafes: List<CafeDTO>,
    val color: String,
    val name: String
)