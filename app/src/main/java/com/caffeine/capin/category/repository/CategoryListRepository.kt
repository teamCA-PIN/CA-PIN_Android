package com.caffeine.capin.category.repository

import com.caffeine.capin.category.CategoryNameEntity
import io.reactivex.rxjava3.core.Single

interface CategoryListRepository {
    fun getCategoryList(): Single<List<CategoryNameEntity>>
}