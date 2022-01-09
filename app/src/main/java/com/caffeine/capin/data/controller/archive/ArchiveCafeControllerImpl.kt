package com.caffeine.capin.data.controller.archive

import com.caffeine.capin.data.dto.category.RequestArchiveCafeDTO
import com.caffeine.capin.data.network.BaseResponse
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ArchiveCafeControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): ArchiveCafeController {
    override fun archiveCafe(cafeId: String, categoryId: RequestArchiveCafeDTO): Single<BaseResponse> =
        capinApiService.archiveCafeToCategory(cafeId, categoryId)
}