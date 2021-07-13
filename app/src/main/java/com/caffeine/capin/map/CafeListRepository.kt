package com.caffeine.capin.map

import io.reactivex.rxjava3.core.Single

class CafeListRepository(
    private val cafeListDataSource: CafeListDataSource,
    private val cafeListMapper: CafeListMapper,
    private val cafeDetailMapper: CafeDetailMapper
) {
    fun getCafeList(tags: List<RequestTag>?): Single<List<CafeInformationEntity>> =
        cafeListDataSource.getCafeList(tags).map { list ->
            list.cafeLocations.map{
                cafeListMapper.map(it)
            }
        }

    fun getCafeDetail(cafeId: String): Single<CafeDetailEntity> =
        cafeListDataSource.getCafeDetail(cafeId).map {
            cafeDetailMapper.map(it)
        }
}