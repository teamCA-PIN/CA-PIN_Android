package com.caffeine.capin.data.controller.archive

import io.reactivex.rxjava3.core.Completable

interface UnArchiveCafeController {
    fun unarchiveCafe(categoryId: String, cafeList: List<String>): Completable
}