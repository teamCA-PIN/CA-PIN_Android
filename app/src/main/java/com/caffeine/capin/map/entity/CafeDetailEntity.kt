package com.caffeine.capin.map.entity

import com.caffeine.capin.tagfilter.model.CafeTagEntity

data class CafeDetailEntity(
    val tags: List<CafeTagEntity>,
    val _id: String,
    val name: String,
    val address: String,
    val instagram: String?,
    val opentime: String?,
    val opentimeHoliday: String?,
    val closetime: String?,
    val closetimeHoliday: String?,
    val offday: List<String>?,
    val latitude: Double,
    val longitude: Double,
    val average: Float?
)