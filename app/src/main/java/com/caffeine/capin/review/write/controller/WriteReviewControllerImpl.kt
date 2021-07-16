package com.caffeine.capin.review.write.controller

import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.review.write.controller.WriteReviewController
import io.reactivex.rxjava3.core.Completable
import okhttp3.MultipartBody
import javax.inject.Inject

class WriteReviewControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): WriteReviewController {
    override fun postReview(cafeId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Completable =
        capinApiService.postReview(cafeId, review,imgs)
}