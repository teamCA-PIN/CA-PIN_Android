package com.caffeine.capin.data.controller.review

import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Completable
import okhttp3.MultipartBody
import javax.inject.Inject

class WriteReviewControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): WriteReviewController {
    override fun postReview(cafeId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Completable =
        capinApiService.postReview(cafeId, review,imgs)
}