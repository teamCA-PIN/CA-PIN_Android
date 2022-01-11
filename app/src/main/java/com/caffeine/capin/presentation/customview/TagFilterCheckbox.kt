package com.caffeine.capin.presentation.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.caffeine.capin.R

class TagFilterCheckbox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : CompoundButton(context, attributeSet, defStyle) {

    init {
        setBackgroundResource(R.drawable.selector_tagfilter)
        buttonDrawable = null
        gravity = Gravity.CENTER
        setFontFamily()
        setOnClickListener { }
        setTextColor()
    }

    private fun setFontFamily() {
        typeface = Typeface.create(
            ResourcesCompat.getFont(context, R.font.noto_sans_kr_medium),
            Typeface.NORMAL
        )
    }

    fun setTextColor() {
        setTextColor(
            ContextCompat.getColorStateList(
                context,
                R.color.selector_tagfilter_text
            )
        )
    }

}