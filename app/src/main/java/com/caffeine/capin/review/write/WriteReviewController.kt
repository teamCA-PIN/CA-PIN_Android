package com.caffeine.capin.review.write

import com.caffeine.capin.network.BaseResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface WriteReviewController {
    fun postReview(cafeId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Single<BaseResponse>
}