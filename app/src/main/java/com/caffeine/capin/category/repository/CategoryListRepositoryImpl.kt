package com.caffeine.capin.category.repository

import com.caffeine.capin.category.CategoryListMapper
import com.caffeine.capin.category.CategoryNameEntity
import com.caffeine.capin.category.datasource.CategoryListDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryListRepositoryImpl @Inject constructor(
    private val categoryListDataSource: CategoryListDataSource,
    private val categoryListMapper: CategoryListMapper
): CategoryListRepository {
    override fun getCategoryList(): Single<List<CategoryNameEntity>> =
        categoryListDataSource.getCategoryList().map {
            it.myCategoryList.map { category ->
                categoryListMapper.map(category)
            }
        }
}