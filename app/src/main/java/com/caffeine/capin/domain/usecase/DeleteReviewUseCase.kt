package com.caffeine.capin.domain.usecase

import com.caffeine.capin.data.controller.review.EditReviewController
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DeleteReviewUseCase @Inject constructor(
    private val editReviewController: EditReviewController
) {
    operator fun invoke(reviewId: String): Completable =
        editReviewController.deleteReview(reviewId)
}