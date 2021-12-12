package com.caffeine.capin.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created By Malibin
 * on 7월 09, 2021
 */

@Parcelize
data class CafeDetails(
    val id: String,
    val cafeName: String,
    val mainImageUrl: String?,
    val starRate: Double,
    val address: String,
    val tags: List<String>,
    val instagramId: String,
    val operationTime: String,
    val isSaved: Boolean?
) : Parcelable
