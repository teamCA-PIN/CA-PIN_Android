package com.caffeine.capin.category

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryListRepositoryImpl @Inject constructor(
    private val categoryListDataSource: CategoryListDataSource,
    private val categoryListMapper: CategoryListMapper
):CategoryListRepository {
    override fun getCategoryList(): Single<List<CategoryNameEntity>> =
        categoryListDataSource.getCategoryList().map {
            it.myCategoryList.map { category ->
                categoryListMapper.map(category)
            }
        }
}