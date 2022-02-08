package com.caffeine.capin.data.controller.review

import com.caffeine.capin.data.network.BaseResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody

interface WriteReviewController {
    fun postReview(cafeId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Completable
}