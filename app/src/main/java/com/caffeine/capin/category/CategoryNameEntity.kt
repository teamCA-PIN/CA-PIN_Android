package com.caffeine.capin.category

import com.caffeine.capin.R
import java.lang.IllegalArgumentException

data class CategoryNameEntity(
    val hexCode: String,
    val name: String
) {
    enum class CategoryPalette(val hexCode:String,val colorRes: Int){
        CATEGORY_1("6492f5", R.color.category_1),
        CATEGORY_2("6bbc9a", R.color.category_2),
        CATEGORY_3("ffc24b", R.color.category_3),
        CATEGORY_4("816f7c", R.color.category_4),
        CATEGORY_5("ffc2d5", R.color.category_5),
        CATEGORY_6("c9d776", R.color.category_6),
        CATEGORY_7("b2b9e5", R.color.category_7),
        CATEGORY_8("ff8e8e", R.color.category_8),
        CATEGORY_9("ebeaef", R.color.category_9),
        CATEGORY_10("9dc5e8", R.color.category_10);

        companion object {
            fun findColorResource(hexCode: String):Int {
                return values().find { it.hexCode == hexCode }?.colorRes ?: throw IllegalArgumentException("Not Found Color Resources")
            }
        }
    }

}
