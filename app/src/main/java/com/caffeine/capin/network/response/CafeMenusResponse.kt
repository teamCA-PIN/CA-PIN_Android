package com.caffeine.capin.network.response

import com.caffeine.capin.detail.menus.CafeMenu

/**
 * Created By Malibin
 * on 7월 15, 2021
 */

data class CafeMenusResponse(
    val message: String,
    private val menus: List<Item>,
) {
    fun getCafeMenus(): List<CafeMenu> = menus.map { it.toCafeMenu() }

    data class Item(
        val name: String,
        val price: Int,
    ) {
        fun toCafeMenu() = CafeMenu(
            name = name,
            price = price,
        )
    }
}
