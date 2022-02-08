package com.caffeine.capin.di

import com.caffeine.capin.data.controller.archive.ArchiveCafeController
import com.caffeine.capin.data.controller.archive.ArchiveCafeControllerImpl
import com.caffeine.capin.data.controller.archive.UnArchiveCafeController
import com.caffeine.capin.data.controller.archive.UnArchiveControllerImpl
import com.caffeine.capin.data.controller.cafeti.CafetiController
import com.caffeine.capin.data.controller.cafeti.CafetiControllerImpl
import com.caffeine.capin.data.controller.review.*
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.network.TokenController
import com.caffeine.capin.data.network.TokenControllerImpl
import com.caffeine.capin.data.network.UnAuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Completable
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

    @Provides
    @Singleton
    fun provideReportReviewController(capinApiService: CapinApiService): ReportReviewController =
        ReportReviewControllerImpl(capinApiService)

    @Provides
    @Singleton
    fun provideEditReviewController(capinApiService: CapinApiService): EditReviewController =
        EditReviewControllerImpl(capinApiService)

    @Provides
    @Singleton
    fun provideUnArchiveCafeController(capinApiService: CapinApiService): UnArchiveCafeController =
        UnArchiveControllerImpl(capinApiService)

    @Provides
    @Singleton
    fun provideCafetiController(capinApiService: CapinApiService): CafetiController =
        CafetiControllerImpl(capinApiService)
}