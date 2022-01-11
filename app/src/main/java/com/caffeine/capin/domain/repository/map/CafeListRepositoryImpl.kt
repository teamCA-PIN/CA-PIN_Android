package com.caffeine.capin.domain.repository.map

import com.caffeine.capin.data.mapper.CafeListMapper
import com.caffeine.capin.data.source.map.CafeListDataSource
import com.caffeine.capin.domain.entity.map.CafeDetailEntity
import com.caffeine.capin.domain.entity.map.CafeInformationEntity
import com.caffeine.capin.data.mapper.CafeDetailMapper
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