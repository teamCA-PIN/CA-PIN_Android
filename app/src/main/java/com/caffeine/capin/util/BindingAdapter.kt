package com.caffeine.capin.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("load_url")
    @JvmStatic
    fun ImageView.loadImageView(url: String?) {
        Glide.with(context).load(url).into(this)
    }
}