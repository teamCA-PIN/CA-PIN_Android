package com.caffeine.capin.data.source.map

import com.caffeine.capin.data.dto.cafedetail.ResponseCafeDetail
import com.caffeine.capin.data.dto.map.ResponseCafeList
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Single

class CafeListDataSourceImpl(private val capinApiService: CapinApiService) : CafeListDataSource {
    override fun getCafeList(tags: List<Int?>): Single<ResponseCafeList> = capinApiService.getCafeLocationList(tags)

    override fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail> =
        capinApiService.getCafeDetail(cafeId)
}