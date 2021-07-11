package com.caffeine.capin.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.caffeine.capin.R

class CapinActiveInActiveButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int =0): AppCompatButton(context, attrs, defStyle) {

    init {
        clipToOutline = true
        setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        setTextAppearance(R.style.Subhead)
        gravity = Gravity.CENTER
        textAlignment = TEXT_ALIGNMENT_CENTER
        setTextColor(ContextCompat.getColor(context, R.color.white))
        inactiveButton()
    }

    fun activeButton() {
        isClickable = true
        setBackgroundResource(R.drawable.write_review_checkbox_selected)
    }

    fun inactiveButton() {
        isClickable = false
        setBackgroundResource(R.drawable.background_post_review_button_inactive)
    }
}