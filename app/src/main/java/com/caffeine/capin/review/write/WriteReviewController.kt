package com.caffeine.capin.review.write

import com.caffeine.capin.network.BaseResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface WriteReviewController {
    fun postReview(review: MultipartBody.Part, imgs: Map<String, RequestBody>): Single<BaseResponse>
}