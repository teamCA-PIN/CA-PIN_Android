package com.caffeine.capin.customview

import android.content.Context
import android.content.res.Resources
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ToastMessageCapinBinding

object CapinToastMessage{
    fun createCapinToast(context: Context, message: String, vertical: Int?): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastMessageCapinBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_message_capin, null, false)

        binding.textviewMessage.text = message

        return Toast(context).apply {
            if (vertical != null) {
                setGravity(Gravity.BOTTOM or Gravity.CENTER, 0 ,vertical!!.toPx())
            } else {
                setGravity(Gravity.BOTTOM or Gravity.CENTER, 0 ,60.toPx())

            }
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}