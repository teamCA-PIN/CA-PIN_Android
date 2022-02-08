package com.caffeine.capin.util

import android.content.res.Resources
import android.view.View
import android.view.ViewTreeObserver
import java.text.DecimalFormat

/**
 * Created By Malibin
 * on 7월 08, 2021
 */
@JvmName("ViewUtils")

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun moneyTextFormat(price: Int?): String {
    return if (price == null) ""
    else DecimalFormat("#,###").format(price)
}

fun View.drawFinish(callback: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            callback()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}