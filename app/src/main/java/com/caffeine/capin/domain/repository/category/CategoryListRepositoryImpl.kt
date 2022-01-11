package com.caffeine.capin.domain.repository.category

import com.caffeine.capin.data.mapper.CategoryListMapper
import com.caffeine.capin.domain.entity.category.CategoryNameEntity
import com.caffeine.capin.data.source.category.CategoryListDataSource
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