package com.caffeine.capin.data.controller.archive

import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UnArchiveControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): UnArchiveCafeController{
    override fun unarchiveCafe(categoryId: String, cafeList: List<String>): Completable =
        capinApiService.unArchiveCafe(categoryId, cafeList)
}