package com.caffeine.capin.util

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.caffeine.capin.customview.CapinChip
import com.google.android.material.chip.ChipGroup

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

    @BindingAdapter("app:rounded5Image")
    fun bindingRoundImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl == null) return
        Glide.with(imageView)
            .load(imageUrl)
            .transform(RoundedCorners(5.toPx()), CenterCrop())
            .into(imageView)
    }

    @BindingAdapter("app:chipTitles")
    fun bindingChipsToChipGroup(chipGroup: ChipGroup, chipTitles: List<String>?) {
        if (chipTitles.isNullOrEmpty()) return
        chipGroup.removeAllViews()
        chipTitles.map { CapinChip.create(chipGroup.context).apply { text = it } }
            .forEach { chipGroup.addView(it) }
    }
}
