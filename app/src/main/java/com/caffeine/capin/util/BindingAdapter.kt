package com.caffeine.capin.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

object BindingAdapter {
    @BindingAdapter("load_url")
    @JvmStatic
    fun ImageView.loadImageView(url: String?) {
        Glide.with(context).load(url).into(this)
    }

    @BindingAdapter("app:useCircleOutlineRadius")
    @JvmStatic
    fun ShapeableImageView.useCircleOutline(radius: Float) {
        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
    }
}