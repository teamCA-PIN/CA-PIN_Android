package com.caffeine.capin.data.controller.cafeti

import com.caffeine.capin.data.dto.cafeti.RequestCafetiData
import com.caffeine.capin.data.dto.cafeti.ResponseCafetiData
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CafetiControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): CafetiController {
    override fun fetchCafetiResult(answers: RequestCafetiData): Single<ResponseCafetiData> =
        capinApiService.postCafeti(answers)
}