package com.caffeine.capin.category

import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ArchiveCafeControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): ArchiveCafeController {
    override fun archiveCafe(cafeId: String, categoryId: RequestArchiveCafeDTO): Single<BaseResponse> =
        capinApiService.archiveCafeToCategory(cafeId, categoryId)
}