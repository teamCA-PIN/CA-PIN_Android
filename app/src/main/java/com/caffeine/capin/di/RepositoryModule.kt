package com.caffeine.capin.di

import com.caffeine.capin.map.CafeDetailMapper
import com.caffeine.capin.map.CafeListDataSource
import com.caffeine.capin.map.CafeListMapper
import com.caffeine.capin.map.CafeListRepository
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
        ): CafeListRepository = CafeListRepository(datasource,cafeListMapper, cafeDetailMapper)


}