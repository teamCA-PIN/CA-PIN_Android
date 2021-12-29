package com.caffeine.capin.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.caffeine.capin.R
import com.caffeine.capin.util.toPx
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CustomToastTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    private val message: String,
    private val icon: Int?,
    private val drawable: Int?,
    private val yLocation: Double,
    private val root: ViewGroup,
): AppCompatTextView(context, attrs, -1) {
    private val displayMetrics = resources.displayMetrics

    init {
        id = this.hashCode()
        background = ContextCompat.getDrawable(context, R.drawable.shape_toast)
        typeface = ResourcesCompat.getFont(context, R.font.noto_sans_kr_regular)
        setTextColor(ContextCompat.getColor(context, R.color.white))
        textSize = 12f
        gravity = Gravity.CENTER_VERTICAL
        setPadding(20.toPx(), 4.toPx(), 20.toPx(),4.toPx())

        setLayoutParams()

        visibility = View.GONE
        text = message
        root.addView(this)
        setMessage()
        setHeight()
    }

    private fun setLayoutParams() {
        var customParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        customParams.startToStart = root.id
        customParams.endToEnd = root.id

        val coordinatorLayoutParams = CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT)
        coordinatorLayoutParams.gravity = Gravity.CENTER_HORIZONTAL

        layoutParams = if (root is CoordinatorLayout) coordinatorLayoutParams else customParams
    }

    private fun setMessage() {
        if(icon != null) {
            setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, icon), null, null, null)
            compoundDrawablePadding = 12.toPx()
        }
    }

    private fun setHeight() {
        root.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                showMessage()
                root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun showMessage() {
        val animationSet = AnimationSet(true)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        val translateAnimation = TranslateAnimation(0f,0f, (root.height * yLocation).toFloat() + 40f, (root.height * yLocation).toFloat())

        animationSet.run {
            duration = 550
            fillAfter = true
            addAnimation(alphaAnimation)
            addAnimation(translateAnimation)
            startAnimation(this)
        }

        animationSet.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                Single.timer(800, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        visibility = View.GONE
                        (root as ViewGroup).removeView(this@CustomToastTextView)
                    }, {
                        it.printStackTrace()
                    })
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
    }
}