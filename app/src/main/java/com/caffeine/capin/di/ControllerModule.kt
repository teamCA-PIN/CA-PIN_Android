package com.caffeine.capin.di

import com.caffeine.capin.category.ArchiveCafeController
import com.caffeine.capin.category.ArchiveCafeControllerImpl
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.review.write.WriteReviewController
import com.caffeine.capin.review.write.WriteReviewControllerImpl
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
    fun provideWriteReviewController(capinApiService: CapinApiService): WriteReviewController = WriteReviewControllerImpl(capinApiService)

    @Provides
    @Singleton
    fun provideArchiveCafeController(capinApiService: CapinApiService): ArchiveCafeController = ArchiveCafeControllerImpl(capinApiService)
}