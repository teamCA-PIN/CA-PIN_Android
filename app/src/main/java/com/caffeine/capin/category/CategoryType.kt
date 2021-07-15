package com.caffeine.capin.category

import com.caffeine.capin.R
import java.lang.IllegalArgumentException

enum class CategoryType(val hexCode: String, val colorRes: Int, val inactive: Int, val active: Int) {
    CATEGORY1("6492F5", R.color.category_1, R.drawable.ic_pin_inactive_cate_1, R.drawable.ic_pin_active_cate_1),
    CATEGORY2("6BBC9A", R.color.category_2,R.drawable.ic_pin_inactive_cate_2, R.drawable.ic_pin_active_cate_2),
    CATEGORY3("FFC24B", R.color.category_3,R.drawable.ic_pin_inactive_cate_3, R.drawable.ic_pin_active_cate_3),
    CATEGORY4("816F7C", R.color.category_4,R.drawable.ic_pin_inactive_cate_4, R.drawable.ic_pin_active_cate_4),
    CATEGORY5("FFC2D5", R.color.category_5,R.drawable.ic_pin_inactive_cate_5, R.drawable.ic_pin_active_cate_5),
    CATEGORY6("C9D776", R.color.category_6,R.drawable.ic_pin_inactive_cate_6, R.drawable.ic_pin_active_cate_6),
    CATEGORY7("B2B9E5", R.color.category_7,R.drawable.ic_pin_inactive_cate_7, R.drawable.ic_pin_active_cate_7),
    CATEGORY8("FF8E8E", R.color.category_8,R.drawable.ic_pin_inactive_cate_8, R.drawable.ic_pin_active_cate_8),
    CATEGORY9("EBEAEF", R.color.category_9,R.drawable.ic_pin_inactive_cate_9, R.drawable.ic_pin_active_cate_9),
    CATEGORY10("9DC5E8", R.color.category_10,R.drawable.ic_pin_inactive_cate_10, R.drawable.ic_pin_active_cate_10);


    companion object {
        fun findInactiveType(hexCode: String): Int {
            return CategoryType.values().find { it.hexCode == hexCode }
                ?.inactive ?: throw IllegalArgumentException("Color Type Error")
        }

        fun findActiveType(hexCode: String): Int {
            return CategoryType.values().find { it.hexCode == hexCode }
                ?.active ?: throw IllegalArgumentException("Color Type Error")
        }

        fun findColorResource(hexCode: String):Int {
            return CategoryType.values().find { it.hexCode == hexCode }?.colorRes ?: throw IllegalArgumentException("Not Found Color Resources")
        }


    }
}