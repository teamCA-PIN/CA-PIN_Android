package com.caffeine.capin.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.caffeine.capin.databinding.MapToolbarCapinBinding

class CapinMapToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: MapToolbarCapinBinding =
        MapToolbarCapinBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.root.apply {
            clipToOutline = true
        }
    }

    fun setTagSearchButton(listener: OnClickListener) {
        binding.buttonTag.setOnClickListener {
            listener.onClick(this)
        }
    }

    fun changeTagSearchBackground(background: Int) {
        binding.buttonTag.setBackgroundResource(background)
    }

    fun setMenuButton(listener: OnClickListener) {
        binding.buttonMenu.setOnClickListener {
            listener.onClick(this)
        }
    }

}