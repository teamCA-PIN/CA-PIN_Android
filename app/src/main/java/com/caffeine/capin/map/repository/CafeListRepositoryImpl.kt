package com.caffeine.capin.map.repository

import com.caffeine.capin.map.mapper.CafeListMapper
import com.caffeine.capin.map.datasource.CafeListDataSource
import com.caffeine.capin.map.entity.CafeDetailEntity
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.map.mapper.CafeDetailMapper
import io.reactivex.rxjava3.core.Single

class CafeListRepositoryImpl(
    private val cafeListDataSource: CafeListDataSource,
    private val cafeListMapper: CafeListMapper,
    private val cafeDetailMapper: CafeDetailMapper
): CafeListRepository {
    override fun getCafeList(tags: List<Int?>): Single<List<CafeInformationEntity>> =
        cafeListDataSource.getCafeList(tags).map { list ->
            list.cafeLocations.map {
                cafeListMapper.map(it)
            }
        }

    override fun getCafeDetail(cafeId: String): Single<CafeDetailEntity> =
        cafeListDataSource.getCafeDetail(cafeId).map {
            cafeDetailMapper.map(it)
        }
}