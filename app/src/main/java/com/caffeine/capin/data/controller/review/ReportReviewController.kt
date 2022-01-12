package com.caffeine.capin.data.controller.review

import io.reactivex.rxjava3.core.Completable

interface ReportReviewController {
    fun reportReview(reviewId: String): Completable
}