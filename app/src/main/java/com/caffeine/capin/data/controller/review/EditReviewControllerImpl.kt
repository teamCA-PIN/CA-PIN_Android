package com.caffeine.capin.data.controller.review

import com.caffeine.capin.data.network.CapinApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class EditReviewControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): EditReviewController {
    override fun modifyReview(reviewId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>?) =
        capinApiService.modifyReview(reviewId, review, imgs)
    override fun deleteReview(reviewId: String) =
        capinApiService.deleteReview(reviewId)
}