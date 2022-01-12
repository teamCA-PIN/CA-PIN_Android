package com.caffeine.capin.domain.usecase

import com.caffeine.capin.data.controller.archive.UnArchiveCafeController
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UnArchiveCafeUseCase @Inject constructor(
    private val unArchiveCafeController: UnArchiveCafeController
) {
    operator fun invoke(categoryId: String, cafeList: List<String>): Completable =
        unArchiveCafeController.unarchiveCafe(categoryId, cafeList)
}