package com.caffeine.capin.data.dto.cafedetail

import com.caffeine.capin.domain.entity.cafedetail.CafeMenuEntity

/**
 * Created By Malibin
 * on 7월 15, 2021
 */

data class CafeMenusResponse(
    val message: String,
    private val menus: List<Item>,
) {
    fun getCafeMenus(): List<CafeMenuEntity> = menus.map { it.toCafeMenu() }

    data class Item(
        val name: String,
        val price: Int,
    ) {
        fun toCafeMenu() = CafeMenuEntity(
            name = name,
            price = price,
        )
    }
}
