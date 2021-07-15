package com.caffeine.capin.di

import com.caffeine.capin.category.CategoryListDataSource
import com.caffeine.capin.category.CategoryListDataSourceImpl
import com.caffeine.capin.map.datasource.CafeListDataSource
import com.caffeine.capin.map.datasource.CafeListDataSourceImpl
import com.caffeine.capin.map.datasource.MyMapLocationsDataSource
import com.caffeine.capin.map.datasource.MyMapLocationsDataSourceImpl
import com.caffeine.capin.network.CapinApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCafeListDataSource(capinApiService: CapinApiService): CafeListDataSource = CafeListDataSourceImpl(capinApiService)

    @Provides
    @Singleton
    fun provideMyMapLocationsDataSource(capinApiService: CapinApiService): MyMapLocationsDataSource = MyMapLocationsDataSourceImpl(capinApiService)

    @Provides
    @Singleton
    fun provideCategoryListDataSource(capinApiService: CapinApiService): CategoryListDataSource = CategoryListDataSourceImpl(capinApiService)
}