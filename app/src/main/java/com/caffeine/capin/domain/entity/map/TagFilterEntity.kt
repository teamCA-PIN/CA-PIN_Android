package com.caffeine.capin.domain.entity.map

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagFilterEntity(
    val tagFilter: String,
    val tagIndex: Int
): Parcelable