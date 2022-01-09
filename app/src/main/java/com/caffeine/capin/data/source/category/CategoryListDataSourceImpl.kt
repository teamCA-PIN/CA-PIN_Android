package com.caffeine.capin.data.source.category

import com.caffeine.capin.data.dto.mypage.ResponseMyCategoryData
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryListDataSourceImpl @Inject constructor(
    private val capinApiService: CapinApiService
): CategoryListDataSource {
    override fun getCategoryList(): Single<ResponseMyCategoryData> =
        capinApiService.getCategoryList()
}