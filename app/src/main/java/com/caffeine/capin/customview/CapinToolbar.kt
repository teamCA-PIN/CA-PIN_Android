package com.caffeine.capin.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.caffeine.capin.databinding.ToolbarCapinBinding

class CapinToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ToolbarCapinBinding =
        ToolbarCapinBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTagSearchButton(listener: OnClickListener) {
        binding.buttonTag.setOnClickListener {
            listener.onClick(this)
        }
    }

    fun setMenuButton(listener: OnClickListener) {
        binding.buttonMenu.setOnClickListener {
            listener.onClick(this)
        }
    }

}