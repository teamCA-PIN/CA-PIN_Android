package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import io.reactivex.rxjava3.core.Single

interface CafeListDataSource {
    fun getCafeList(
        tag1: Int?,
        tag2: Int?,
        tag3: Int?,
        tag4: Int?,
        tag5: Int?,
        tag6: Int?
        ): Single<ResponseCafeList>

    fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail>
}