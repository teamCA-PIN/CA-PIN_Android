package com.caffeine.capin.customview

import android.content.Context
import android.view.ViewGroup
import com.caffeine.capin.R

class CustomToastBuilder(private val context: Context, private val message: String, private val root: ViewGroup) {
    private var icon: Int? = null
    private var background: Int = R.drawable.shape_toast
    private var yLocation = 0.9

    fun build(): CustomToastTextView {
        return CustomToastTextView(
            context,
            null,
            message,
            icon,
            background,
            yLocation,
            root
        )
    }

    fun setIcon(icon: Int): CustomToastBuilder {
        this.icon = icon
        return this
    }

    fun setBackgroundDrawable(background: Int): CustomToastBuilder {
        this.background = background
        return this
    }

    fun setYLocation(y: Double): CustomToastBuilder {
        this.yLocation = y
        return this
    }
}