package com.caffeine.capin.data.mapper

import com.caffeine.capin.domain.entity.category.CategoryNameEntity
import com.caffeine.capin.data.dto.category.MyCategory

class CategoryListMapper: DTOMapper<MyCategory, CategoryNameEntity> {
    override fun map(from: MyCategory): CategoryNameEntity =
        CategoryNameEntity(
            from._id,
            from.color,
            from.name
        )

}