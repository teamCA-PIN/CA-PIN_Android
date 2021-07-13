package com.caffeine.capin.map

import io.reactivex.rxjava3.core.Single

interface CafeListDataSource {
    fun getCafeList(tags: List<RequestTag>?): Single<ResponseCafeList>
    fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail>
}