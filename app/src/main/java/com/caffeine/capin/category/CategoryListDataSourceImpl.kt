package com.caffeine.capin.category

import com.caffeine.capin.mypage.api.response.ResponseMyCategoryData
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import javax.inject.Inject

class CategoryListDataSourceImpl @Inject constructor(
    private val capinApiService: CapinApiService
): CategoryListDataSource {
    override fun getCategoryList(): Single<ResponseMyCategoryData> =
        capinApiService.getCategoryList()
}