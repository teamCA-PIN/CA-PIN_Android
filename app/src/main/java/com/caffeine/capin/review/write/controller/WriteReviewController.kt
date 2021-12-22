package com.caffeine.capin.review.write.controller

import com.caffeine.capin.mypage.api.request.RequestPutReviewData
import com.caffeine.capin.network.BaseResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface WriteReviewController {
    fun postReview(cafeId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Completable
    fun modifyReview(reviewId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>): Single<BaseResponse>
}