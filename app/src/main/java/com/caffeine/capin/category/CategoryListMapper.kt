package com.caffeine.capin.category

import com.caffeine.capin.mypage.mycategory.MyCategory
import com.caffeine.capin.network.DTOMapper

class CategoryListMapper: DTOMapper<MyCategory, CategoryNameEntity> {
    override fun map(from: MyCategory): CategoryNameEntity =
        CategoryNameEntity(
            from._id,
            from.color,
            from.name
        )

}