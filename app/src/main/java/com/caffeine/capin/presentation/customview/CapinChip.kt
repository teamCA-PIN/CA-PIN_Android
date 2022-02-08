package com.caffeine.capin.presentation.customview

import android.content.Context
import android.view.LayoutInflater
import com.caffeine.capin.R
import com.google.android.material.chip.Chip

/**
 * Created By Malibin
 * on 7월 08, 2021
 */

object CapinChip {
    fun create(context: Context): Chip {
        return LayoutInflater.from(context)
            .inflate(R.layout.chip_tag, null, false) as Chip
    }
}
