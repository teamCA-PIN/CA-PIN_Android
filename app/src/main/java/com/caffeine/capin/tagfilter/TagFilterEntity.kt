package com.caffeine.capin.tagfilter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagFilterEntity(
    val tagFilter: String,
    val tagIndex: Int
): Parcelable