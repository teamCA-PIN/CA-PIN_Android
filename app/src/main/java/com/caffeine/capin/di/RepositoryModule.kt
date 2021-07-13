package com.caffeine.capin.di

import com.caffeine.capin.map.mapper.CafeDetailMapper
import com.caffeine.capin.map.datasource.CafeListDataSource
import com.caffeine.capin.map.CafeListMapper
import com.caffeine.capin.map.repository.CafeListRepository
import com.caffeine.capin.map.repository.CafeListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCafeListMapper():CafeListMapper = CafeListMapper()

    @Provides
    @Singleton
    fun provideCafeDetailMapper(): CafeDetailMapper = CafeDetailMapper()

    @Provides
    @Singleton
    fun provideCafeListRepository(
        datasource: CafeListDataSource,
        cafeListMapper: CafeListMapper,
        cafeDetailMapper: CafeDetailMapper
        ): CafeListRepository = CafeListRepositoryImpl(datasource,cafeListMapper, cafeDetailMapper)


}