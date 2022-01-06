package com.caffeine.capin.detail

import android.os.Parcelable
import com.caffeine.capin.map.entity.CafeDetailEntity
import kotlinx.android.parcel.Parcelize

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
) : Parcelable {
    fun toCafeDetailEntity(): CafeDetailEntity {
        return CafeDetailEntity(
            null,
            id,
            cafeName,
            address,
            instagramId,
            operationTime,
            null,
            null,
            null,
            null,
            0.0,
            0.0,
            starRate.toFloat(),
            mainImageUrl,
            isSaved ?: false
        )
    }
}
