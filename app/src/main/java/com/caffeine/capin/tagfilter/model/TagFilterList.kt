package com.caffeine.capin.tagfilter.model

import android.os.Parcelable
import com.caffeine.capin.tagfilter.model.TagFilterEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
class TagFilterList: ArrayList<TagFilterEntity?>(), Parcelable