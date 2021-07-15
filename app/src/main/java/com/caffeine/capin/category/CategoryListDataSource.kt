package com.caffeine.capin.category

import com.caffeine.capin.mypage.api.response.ResponseMyCategoryData
import io.reactivex.rxjava3.core.Single

interface CategoryListDataSource {
    fun getCategoryList() : Single<ResponseMyCategoryData>
}