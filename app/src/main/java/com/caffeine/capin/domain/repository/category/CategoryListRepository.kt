package com.caffeine.capin.domain.repository.category

import com.caffeine.capin.domain.entity.category.CategoryNameEntity
import io.reactivex.rxjava3.core.Single

interface CategoryListRepository {
    fun getCategoryList(): Single<List<CategoryNameEntity>>
}