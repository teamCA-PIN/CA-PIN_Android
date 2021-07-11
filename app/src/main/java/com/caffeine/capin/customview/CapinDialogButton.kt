package com.caffeine.capin.customview

import android.content.Context

data class CapinDialogButton(
    val text: String,
    val textColor: Int,
    val context: Context,
    var listener: OnClickListener?
) {
    interface OnClickListener {
        fun onClick()
    }
}