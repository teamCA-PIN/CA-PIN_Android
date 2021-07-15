package com.caffeine.capin.category

import io.reactivex.rxjava3.core.Single

interface CategoryListRepository {
    fun getCategoryList(): Single<List<CategoryNameEntity>>
}