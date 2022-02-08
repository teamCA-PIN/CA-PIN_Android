package com.caffeine.capin.di

import com.caffeine.capin.data.controller.archive.UnArchiveCafeController
import com.caffeine.capin.data.controller.archive.UnArchiveControllerImpl
import com.caffeine.capin.data.controller.cafeti.CafetiController
import com.caffeine.capin.data.controller.review.EditReviewController
import com.caffeine.capin.data.controller.review.ReportReviewController
import com.caffeine.capin.domain.usecase.DeleteReviewUseCase
import com.caffeine.capin.domain.usecase.GetCafetiResultUseCase
import com.caffeine.capin.domain.usecase.ReportReviewUseCase
import com.caffeine.capin.domain.usecase.UnArchiveCafeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideReportReviewUseCase(reportReviewController: ReportReviewController): ReportReviewUseCase =
        ReportReviewUseCase(reportReviewController)

    @Provides
    @Singleton
    fun provideDeleteReviewUseCase(editReviewController: EditReviewController): DeleteReviewUseCase =
        DeleteReviewUseCase(editReviewController)

    @Provides
    @Singleton
    fun provideUnArchiveCafeUseCase(unArchiveCafeController: UnArchiveCafeController): UnArchiveCafeUseCase =
        UnArchiveCafeUseCase(unArchiveCafeController)

    @Provides
    @Singleton
    fun provideGetCafetiResultUseCase(cafetiController: CafetiController): GetCafetiResultUseCase =
        GetCafetiResultUseCase(cafetiController)
}