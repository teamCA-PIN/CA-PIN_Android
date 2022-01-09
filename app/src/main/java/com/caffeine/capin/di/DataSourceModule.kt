package com.caffeine.capin.di

import com.caffeine.capin.data.source.category.CategoryListDataSource
import com.caffeine.capin.data.source.category.CategoryListDataSourceImpl
import com.caffeine.capin.data.source.map.CafeListDataSource
import com.caffeine.capin.data.source.map.CafeListDataSourceImpl
import com.caffeine.capin.data.source.map.MyMapLocationsDataSource
import com.caffeine.capin.data.source.map.MyMapLocationsDataSourceImpl
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.source.profile.UserProfileDataSource
import com.caffeine.capin.data.source.profile.UserProfileDataSourceImpl
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

    @Provides
    @Singleton
    fun provideUserProfileDataSource(capinApiService: CapinApiService): UserProfileDataSource = UserProfileDataSourceImpl(capinApiService)
}