package com.caffeine.capin.domain.usecase

import com.caffeine.capin.data.controller.cafeti.CafetiController
import com.caffeine.capin.data.dto.cafeti.RequestCafetiData
import com.caffeine.capin.data.dto.cafeti.ResponseCafetiData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.IoScheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetCafetiResultUseCase @Inject constructor(
    private val cafetiController: CafetiController
){
    operator fun invoke(answers: RequestCafetiData): Single<ResponseCafetiData> {
        return cafetiController.fetchCafetiResult(answers)
    }
}