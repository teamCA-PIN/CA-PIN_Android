package com.caffeine.capin.map.repository

import com.caffeine.capin.map.CafeListMapper
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
    override fun getCafeList(
        tag1: Int?,
        tag2: Int?,
        tag3: Int?,
        tag4: Int?,
        tag5: Int?,
        tag6: Int?
    ): Single<List<CafeInformationEntity>> =
        cafeListDataSource.getCafeList(tag1, tag2, tag3, tag4, tag5, tag6).map { list ->
            list.cafeLocations.map {
                cafeListMapper.map(it)
            }
        }

    override fun getCafeDetail(cafeId: String): Single<CafeDetailEntity> =
        cafeListDataSource.getCafeDetail(cafeId).map {
            cafeDetailMapper.map(it)
        }
}