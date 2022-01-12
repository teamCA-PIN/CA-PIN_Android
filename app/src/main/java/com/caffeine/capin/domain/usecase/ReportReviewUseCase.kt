package com.caffeine.capin.domain.usecase

import com.caffeine.capin.data.controller.review.ReportReviewController
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class ReportReviewUseCase @Inject constructor(
    private val reportReviewController: ReportReviewController
) {
    operator fun invoke(reviewId: String): Completable =
         reportReviewController.reportReview(reviewId)
}