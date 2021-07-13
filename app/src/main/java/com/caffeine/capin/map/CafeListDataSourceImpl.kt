package com.caffeine.capin.map

import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single

class CafeListDataSourceImpl(private val capinApiService: CapinApiService): CafeListDataSource {
    override fun getCafeList(tags: List<RequestTag>?): Single<ResponseCafeList> =
        capinApiService.getCafeLocationList(tags)

    override fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail> =
        capinApiService.getCafeDetail(cafeId)
}