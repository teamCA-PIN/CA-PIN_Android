package com.caffeine.capin.di

import com.caffeine.capin.category.datasource.CategoryListDataSource
import com.caffeine.capin.category.CategoryListMapper
import com.caffeine.capin.category.repository.CategoryListRepository
import com.caffeine.capin.category.repository.CategoryListRepositoryImpl
import com.caffeine.capin.map.mapper.CafeDetailMapper
import com.caffeine.capin.map.datasource.CafeListDataSource
import com.caffeine.capin.map.mapper.CafeListMapper
import com.caffeine.capin.map.mapper.MyMapPinMapper
import com.caffeine.capin.map.repository.CafeListRepository
import com.caffeine.capin.map.repository.CafeListRepositoryImpl
import com.caffeine.capin.map.repository.MyMapLocationsRepository
import com.caffeine.capin.map.repository.MyMapLocationsRepositoryImpl
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.profile.datasource.UserProfileDataSource
import com.caffeine.capin.profile.UserProfileMapper
import com.caffeine.capin.profile.repository.UserProfileRepository
import com.caffeine.capin.profile.repository.UserProfileRepositoryImpl
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