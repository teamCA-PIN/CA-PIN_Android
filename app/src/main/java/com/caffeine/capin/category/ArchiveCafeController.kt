package com.caffeine.capin.category

import com.caffeine.capin.network.BaseResponse
import io.reactivex.rxjava3.core.Single

interface ArchiveCafeController {
    fun archiveCafe(cafeId: String, categoryId: RequestArchiveCafeDTO): Single<BaseResponse>
}