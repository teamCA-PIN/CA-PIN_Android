package com.caffeine.capin.category.controller

import com.caffeine.capin.category.model.RequestArchiveCafeDTO
import com.caffeine.capin.network.BaseResponse
import io.reactivex.rxjava3.core.Single

interface ArchiveCafeController {
    fun archiveCafe(cafeId: String, categoryId: RequestArchiveCafeDTO): Single<BaseResponse>
}