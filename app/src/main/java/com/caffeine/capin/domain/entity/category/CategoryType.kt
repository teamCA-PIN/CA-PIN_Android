package com.caffeine.capin.domain.entity.category

import com.caffeine.capin.R
import java.lang.IllegalArgumentException

enum class CategoryType(val hexCode: String, val colorRes: Int, val inactive: Int, val active: Int) {
    CATEGORY1("C12D62", R.color.category_1, R.drawable.ic_pin_inactive_cate_1, R.drawable.ic_pin_active_cate_1),
    CATEGORY2("E57D3A", R.color.category_2,R.drawable.ic_pin_inactive_cate_2, R.drawable.ic_pin_active_cate_2),
    CATEGORY3("FFC24B", R.color.category_3,R.drawable.ic_pin_inactive_cate_3, R.drawable.ic_pin_active_cate_3),
    CATEGORY4("8ABE56", R.color.category_4,R.drawable.ic_pin_inactive_cate_4, R.drawable.ic_pin_active_cate_4),
    CATEGORY5("49A48F", R.color.category_5,R.drawable.ic_pin_inactive_cate_5, R.drawable.ic_pin_active_cate_5),
    CATEGORY6("51BAE0", R.color.category_6,R.drawable.ic_pin_inactive_cate_6, R.drawable.ic_pin_active_cate_6),
    CATEGORY7("1E73BE", R.color.category_7,R.drawable.ic_pin_inactive_cate_7, R.drawable.ic_pin_active_cate_7),
    CATEGORY8("754593", R.color.category_8,R.drawable.ic_pin_inactive_cate_8, R.drawable.ic_pin_active_cate_8),
    CATEGORY9("EBEAEF", R.color.category_9,R.drawable.ic_pin_inactive_cate_9, R.drawable.ic_pin_active_cate_9),
    CATEGORY10("A77145", R.color.category_10,R.drawable.ic_pin_inactive_cate_10, R.drawable.ic_pin_active_cate_10);


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