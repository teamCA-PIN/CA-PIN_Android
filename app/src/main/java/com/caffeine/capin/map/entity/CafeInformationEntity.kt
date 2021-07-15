package com.caffeine.capin.map.entity

import com.caffeine.capin.R
import java.lang.IllegalArgumentException

data class CafeInformationEntity(
    val markType: String,
    val cafeId: String,
    val latitude: Double,
    val longitude: Double
) {
    enum class MarkType(val hexCode: String, val inactive: Int, val active: Int) {
        CATEGORY1("6492F5", R.drawable.ic_pin_inactive_cate_1, R.drawable.ic_pin_active_cate_1),
        CATEGORY2("6BBC9A", R.drawable.ic_pin_inactive_cate_2, R.drawable.ic_pin_active_cate_2),
        CATEGORY3("FFC24B", R.drawable.ic_pin_inactive_cate_3, R.drawable.ic_pin_active_cate_3),
        CATEGORY4("816F7C", R.drawable.ic_pin_inactive_cate_4, R.drawable.ic_pin_active_cate_4),
        CATEGORY5("FFC2D5", R.drawable.ic_pin_inactive_cate_5, R.drawable.ic_pin_active_cate_5),
        CATEGORY6("C9D776", R.drawable.ic_pin_inactive_cate_6, R.drawable.ic_pin_active_cate_6),
        CATEGORY7("B2B9E5", R.drawable.ic_pin_inactive_cate_7, R.drawable.ic_pin_active_cate_7),
        CATEGORY8("FF8E8E", R.drawable.ic_pin_inactive_cate_8, R.drawable.ic_pin_active_cate_8),
        CATEGORY9("EBEAEF", R.drawable.ic_pin_inactive_cate_9, R.drawable.ic_pin_active_cate_9),
        CATEGORY10("9DC5E8", R.drawable.ic_pin_inactive_cate_10, R.drawable.ic_pin_active_cate_10);


        companion object {
            fun findInactiveType(hexCode: String): Int {
                return MarkType.values().find { it.hexCode == hexCode }
                    ?.inactive ?: throw IllegalArgumentException("Color Type Error")
            }

            fun findActiveType(hexCode: String): Int {
                return MarkType.values().find { it.hexCode == hexCode }
                    ?.active ?: throw IllegalArgumentException("Color Type Error")
            }

        }
    }
}