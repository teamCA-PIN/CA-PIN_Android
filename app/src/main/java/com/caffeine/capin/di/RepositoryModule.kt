package com.caffeine.capin.di

import com.caffeine.capin.data.source.category.CategoryListDataSource
import com.caffeine.capin.data.mapper.CategoryListMapper
import com.caffeine.capin.domain.repository.category.CategoryListRepository
import com.caffeine.capin.domain.repository.category.CategoryListRepositoryImpl
import com.caffeine.capin.data.mapper.CafeDetailMapper
import com.caffeine.capin.data.source.map.CafeListDataSource
import com.caffeine.capin.data.mapper.CafeListMapper
import com.caffeine.capin.data.mapper.MyMapPinMapper
import com.caffeine.capin.domain.repository.map.CafeListRepository
import com.caffeine.capin.domain.repository.map.CafeListRepositoryImpl
import com.caffeine.capin.domain.repository.map.MyMapLocationsRepository
import com.caffeine.capin.domain.repository.map.MyMapLocationsRepositoryImpl
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.source.profile.UserProfileDataSource
import com.caffeine.capin.data.mapper.UserProfileMapper
import com.caffeine.capin.domain.repository.profile.UserProfileRepository
import com.caffeine.capin.domain.repository.profile.UserProfileRepositoryImpl
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
    fun provideCategoryListMapper(): CategoryListMapper = CategoryListMapper()

    @Provides
    @Singleton
    fun provideUserProfileMapper(): UserProfileMapper = UserProfileMapper()

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

    @Provides
    @Singleton
    fun provideCategoryListRepository(
        categoryListDataSource: CategoryListDataSource,
        categoryListMapper: CategoryListMapper
    ): CategoryListRepository = CategoryListRepositoryImpl(categoryListDataSource, categoryListMapper)

    @Provides
    @Singleton
    fun provideUserProfileRepository(
        userProfileDataSource: UserProfileDataSource,
        userProfileMapper: UserProfileMapper
    ): UserProfileRepository = UserProfileRepositoryImpl(userProfileDataSource, userProfileMapper)

}