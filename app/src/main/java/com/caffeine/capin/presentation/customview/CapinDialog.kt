package com.caffeine.capin.presentation.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.caffeine.capin.databinding.CapinDialogBinding
import com.caffeine.capin.databinding.CapinDialogButtonBinding

class CapinDialog(
    private val title: String?,
    private val buttonArray:ArrayList<CapinDialogButton>?,
    private val exitButton: Boolean,
    private val contentDialogTitle: String?,
    private val content: String?,
    private val leftButton: String?,
    private val rightButton: String?,
    private val contentDialogButtons: Boolean,
    private val listener: DialogClickListener?
    ): DialogFragment() {
   private lateinit var capinDialogBinding: CapinDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        capinDialogBinding = CapinDialogBinding.inflate(inflater, container, false)
        capinDialogBinding.textviewTitle.text = title
        placeVerticalButtons(inflater, container)

        if (exitButton) {
            capinDialogBinding.textviewExitButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    dismiss()
                }
            }
        }

        if (contentDialogTitle != null) {
            capinDialogBinding.apply {
                textviewTitle.visibility = View.GONE
                linearlayoutButtons.visibility = View.GONE
                textviewContentDialogTitle.visibility = View.VISIBLE
                textviewContentDialogTitle.text = contentDialogTitle
            }
        }

        if(content != null) {
            capinDialogBinding.textviewContent.apply {
                visibility = View.VISIBLE
                text = content
            }
        }

        if (contentDialogButtons) {
            capinDialogBinding.run {
                linearlayoutContentDialogButton.apply {
                    visibility = View.VISIBLE
                    textviewRightButton.text = rightButton
                    textviewRightButton.setOnClickListener{
                        listener?.onClick()
                        dismiss()
                    }
                    textviewLeftButton.text = leftButton
                    textviewLeftButton.setOnClickListener {
                        dismiss()
                    }
                }
            }
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return capinDialogBinding.root
    }

    private fun placeVerticalButtons(inflater: LayoutInflater, container: ViewGroup?) {
        buttonArray.apply {
            buttonArray?.forEach { dialogButton ->
                val buttonBinding = CapinDialogButtonBinding.inflate(inflater, container, false)

                buttonBinding.textviewButton.apply {
                    text = dialogButton.text
                    setTextColor(dialogButton.textColor)
                }
                buttonBinding.root.setOnClickListener {
                    dialogButton.listener?.onClick()
                }
                capinDialogBinding.linearlayoutButtons.addView(buttonBinding.root)
            }
        }
    }
}