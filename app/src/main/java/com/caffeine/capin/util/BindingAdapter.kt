package com.caffeine.capin.util

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("load_url")
    @JvmStatic
    fun ImageView.loadImageView(url: String?) {
        Glide.with(context).load(url).into(this)
    }

    @BindingAdapter("set_color")
    @JvmStatic
    fun ImageView.getCategoryColor(color: String) {
        val hexCode = "#${color}"
        setBackgroundColor(Color.parseColor(hexCode))
    }
}