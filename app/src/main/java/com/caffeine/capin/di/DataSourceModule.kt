package com.caffeine.capin.di

import com.caffeine.capin.map.datasource.CafeListDataSource
import com.caffeine.capin.map.datasource.CafeListDataSourceImpl
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
    fun provideCafeListDataSource(cafeListApiService: CapinApiService): CafeListDataSource = CafeListDataSourceImpl(cafeListApiService)
}