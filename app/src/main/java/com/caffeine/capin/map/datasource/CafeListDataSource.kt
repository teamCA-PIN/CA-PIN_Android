package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.QueryMap

interface CafeListDataSource {
    fun getCafeList(tags: List<Int?>): Single<ResponseCafeList>
    fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail>
}