package com.caffeine.capin.review.write

import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class WriteReviewControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): WriteReviewController {
    override fun postReview(cafeId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Completable =
        capinApiService.postReview(cafeId, review,imgs)
}