package com.caffeine.capin.domain.entity.cafeti

import com.google.gson.annotations.SerializedName

data class CafetiEntity(
    val id: String,
    val type: String,
    val img: String,
    val description: String
)