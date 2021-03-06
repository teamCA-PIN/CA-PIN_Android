package com.caffeine.capin.presentation.util

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.caffeine.capin.R
import com.caffeine.capin.domain.entity.category.CategoryType.Companion.findColorResource
import com.caffeine.capin.presentation.customview.CapinChip
import com.caffeine.capin.util.toPx
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @BindingAdapter("load_drawable")
    @JvmStatic
    fun ImageView.loadDrawable(drawable: Int?) {
        Glide.with(context).load(drawable).into(this)
    }

    @BindingAdapter("load_url")
    @JvmStatic
    fun ImageView.loadImageView(url: String?) {
        if (url != null) {
            Glide.with(context).load(url).into(this)
        } else {
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_3))
        }
    }

    @BindingAdapter("app:loadCafeImageMapView")
    @JvmStatic
    fun ImageView.bindingCafeImageMapview(url: String?) {
        Glide.with(this).load(
            if (url.isNullOrEmpty()) R.drawable.ic_component_86 else url
        ).into(this)
    }

    @BindingAdapter("app:loadCafeMainImage")
    @JvmStatic
    fun bindingCafeMainImage(imageView: ImageView, imageUrl: String?) {
//        if (imageUrl == null) return
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(R.drawable.default_cafe_main_image)
            .into(imageView)
    }

    @BindingAdapter("app:useCircleOutlineRadius")
    @JvmStatic
    fun ShapeableImageView.useCircleOutline(radius: Float) {
        if (radius != null) {
            shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
        }
    }

    @BindingAdapter("set_color")
    @JvmStatic
    fun ImageView.getCategoryColor(color: String) {
        val hexCode = "#${color}"
        setBackgroundColor(Color.parseColor(hexCode))
    }

    @JvmStatic
    @BindingAdapter("app:circleImage")
    fun bindingCircleImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl == null) return
        Glide.with(imageView)
            .load(imageUrl)
            .transform(CircleCrop())
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:rounded5Image")
    fun bindingRoundImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl == null) return
        Glide.with(imageView)
            .load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(5.toPx()))
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:chipTitles")
    fun bindingChipsToChipGroup(chipGroup: FlexboxLayout, chipTitles: List<String>?) {
        if (chipTitles.isNullOrEmpty()) return
        chipGroup.removeAllViews()
        chipTitles.map { CapinChip.create(chipGroup.context).apply { text = it } }
            .forEach {
                chipGroup.addView(it)

                (it.layoutParams as? ViewGroup.MarginLayoutParams)?.updateMargins(
                    right = chipGroup.context.resources.getDimensionPixelSize(R.dimen.chip_margin),
                    left = chipGroup.context.resources.getDimensionPixelSize(R.dimen.chip_margin),
                )
            }
        //fixme: ????????? ??????????????? ??? ??????
    }

    @JvmStatic
    @BindingAdapter("app:date")
    fun bindingTextDate(textView: TextView, date: Date?) {
        if (date == null) return
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        textView.text = dateFormat.format(date)
    }

    @JvmStatic
    @BindingAdapter("load_uri")
    fun ImageView.setImageUri(uri: Uri) {
        Glide.with(context).load(uri).into(this)
    }

    @JvmStatic
    @BindingAdapter("set_background_category")
    fun ImageView.setBackgroundCategory(hexCode: String) {
        setBackgroundColor(ContextCompat.getColor(this.context, findColorResource(hexCode)))
    }
}
