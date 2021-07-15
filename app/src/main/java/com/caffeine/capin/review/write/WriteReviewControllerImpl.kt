package com.caffeine.capin.review.write

import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class WriteReviewControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): WriteReviewController {
    override fun postReview(review: MultipartBody.Part, imgs: Map<String, RequestBody>): Single<BaseResponse> =
        capinApiService.postReview(review,imgs)
}