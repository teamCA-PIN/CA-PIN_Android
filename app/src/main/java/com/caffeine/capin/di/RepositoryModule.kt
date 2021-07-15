package com.caffeine.capin.di

import com.caffeine.capin.map.mapper.CafeDetailMapper
import com.caffeine.capin.map.datasource.CafeListDataSource
import com.caffeine.capin.map.CafeListMapper
import com.caffeine.capin.map.MyMapPinMapper
import com.caffeine.capin.map.datasource.MyMapLocationsDataSourceImpl
import com.caffeine.capin.map.repository.CafeListRepository
import com.caffeine.capin.map.repository.CafeListRepositoryImpl
import com.caffeine.capin.map.repository.MyMapLocationsRepository
import com.caffeine.capin.map.repository.MyMapLocationsRepositoryImpl
import com.caffeine.capin.network.CapinApiService
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
    fun provideCafeListMapper(): CafeListMapper = CafeListMapper()

    @Provides
    @Singleton
    fun provideCafeDetailMapper(): CafeDetailMapper = CafeDetailMapper()

    @Provides
    @Singleton
    fun provideMyMapPinMapper(): MyMapPinMapper = MyMapPinMapper()

    @Provides
    @Singleton
    fun provideCafeListRepository(
        datasource: CafeListDataSource,
        cafeListMapper: CafeListMapper,
        cafeDetailMapper: CafeDetailMapper
    ): CafeListRepository = CafeListRepositoryImpl(datasource, cafeListMapper, cafeDetailMapper)


    @Provides
    @Singleton
    fun provideMyMapLocationsRepository(
        capinApiService: CapinApiService,
        myMapPinMapper: MyMapPinMapper
    ): MyMapLocationsRepository =
        MyMapLocationsRepositoryImpl(capinApiService, myMapPinMapper)

}