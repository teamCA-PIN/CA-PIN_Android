package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single

class CafeListDataSourceImpl(private val capinApiService: CapinApiService) : CafeListDataSource {
    override fun getCafeList(
        tag1: Int?,
        tag2: Int?,
        tag3: Int?,
        tag4: Int?,
        tag5: Int?,
        tag6: Int?
    ): Single<ResponseCafeList> = capinApiService.getCafeLocationList(tag1, tag2, tag3, tag4, tag5, tag6)

    override fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail> =
        capinApiService.getCafeDetail(cafeId)
}