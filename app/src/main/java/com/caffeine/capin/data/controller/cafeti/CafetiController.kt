package com.caffeine.capin.data.controller.cafeti

import com.caffeine.capin.data.dto.cafeti.RequestCafetiData
import com.caffeine.capin.data.dto.cafeti.ResponseCafetiData
import io.reactivex.rxjava3.core.Single

interface CafetiController {
    fun fetchCafetiResult(answers: RequestCafetiData): Single<ResponseCafetiData>
}