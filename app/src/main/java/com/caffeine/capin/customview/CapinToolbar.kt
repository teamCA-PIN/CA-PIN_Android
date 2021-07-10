package com.caffeine.capin.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.caffeine.capin.R
import com.caffeine.capin.databinding.LayoutCapinToolbarBinding

class CapinToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {
    private var binding: LayoutCapinToolbarBinding = LayoutCapinToolbarBinding.inflate(
        LayoutInflater.from(context), this, true)

    init {
        binding.textviewTitle.apply {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
            setTextAppearance(R.style.Headline1)
        }
    }

    fun setToolbarTitle(title: String) {
        binding.textviewTitle.text = title
    }

    fun setBackButton(listener: OnClickListener) {
        binding.imageviewBack.setOnClickListener {
            listener.onClick(this)
        }
    }
}