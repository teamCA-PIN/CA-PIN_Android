package com.caffeine.capin.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ToolbarCapinBinding

class CapinToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ToolbarCapinBinding =
        ToolbarCapinBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.root.apply {
            setBackgroundResource(R.drawable.map_toolbar_raidus_background)
            clipToOutline = true
        }
    }

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