package com.caffeine.capin.util

import android.content.res.Resources
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
