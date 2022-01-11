package com.caffeine.capin.data.controller.archive

import com.caffeine.capin.data.dto.category.RequestArchiveCafeDTO
import com.caffeine.capin.data.network.BaseResponse
import io.reactivex.rxjava3.core.Single

interface ArchiveCafeController {
    fun archiveCafe(cafeId: String, categoryId: RequestArchiveCafeDTO): Single<BaseResponse>
}