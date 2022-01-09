package com.caffeine.capin.di

import com.caffeine.capin.data.controller.archive.ArchiveCafeController
import com.caffeine.capin.data.controller.archive.ArchiveCafeControllerImpl
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.network.TokenController
import com.caffeine.capin.data.network.TokenControllerImpl
import com.caffeine.capin.data.network.UnAuthApiService
import com.caffeine.capin.data.controller.review.WriteReviewController
import com.caffeine.capin.data.controller.review.WriteReviewControllerImpl
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