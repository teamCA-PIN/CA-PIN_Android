package com.caffeine.capin.data.controller.review

import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class ReportReviewControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): ReportReviewController {
    override fun reportReview(reviewId: String): Completable =
        capinApiService.reportReview(reviewId)
}