package com.caffeine.capin.data.source.map

import com.caffeine.capin.data.dto.cafedetail.ResponseCafeDetail
import com.caffeine.capin.data.dto.map.ResponseCafeList
import io.reactivex.rxjava3.core.Single

interface CafeListDataSource {
    fun getCafeList(tags: List<Int?>): Single<ResponseCafeList>
    fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail>
}