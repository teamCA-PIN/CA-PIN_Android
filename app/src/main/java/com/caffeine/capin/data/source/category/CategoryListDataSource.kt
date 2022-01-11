package com.caffeine.capin.data.source.category

import com.caffeine.capin.data.dto.mypage.ResponseMyCategoryData
import io.reactivex.rxjava3.core.Single

interface CategoryListDataSource {
    fun getCategoryList() : Single<ResponseMyCategoryData>
}