package com.caffeine.capin.di

import com.caffeine.capin.category.controller.ArchiveCafeController
import com.caffeine.capin.category.controller.ArchiveCafeControllerImpl
import com.caffeine.capin.network.*
import com.caffeine.capin.review.write.controller.WriteReviewController
import com.caffeine.capin.review.write.controller.WriteReviewControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ControllerModule {
    @Provides
    @Singleton
    fun provideWriteReviewController(capinApiService: CapinApiService): WriteReviewController =
        WriteReviewControllerImpl(capinApiService)

    @Provides
    @Singleton
    fun provideArchiveCafeController(capinApiService: CapinApiService): ArchiveCafeController =
        ArchiveCafeControllerImpl(capinApiService)


    @Provides
    @Singleton
    fun provideTokenController(unAuthApiService: UnAuthApiService): TokenController =
        TokenControllerImpl(unAuthApiService)
}