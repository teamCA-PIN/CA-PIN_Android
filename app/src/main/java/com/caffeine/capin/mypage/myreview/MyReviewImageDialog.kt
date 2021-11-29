package com.caffeine.capin.mypage.myreview

import android.content.Context.WINDOW_SERVICE
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.caffeine.capin.R
import com.caffeine.capin.databinding.DialogMyReviewImageBinding

class MyReviewImageDialog(images: ArrayList<String>) : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    private lateinit var binding: DialogMyReviewImageBinding
    private lateinit var myReviewImageViewPagerAdapter: MyReviewImageViewPagerAdapter
    var imgs = images

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMyReviewImageBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myReviewImageViewPagerAdapter = MyReviewImageViewPagerAdapter(this)
        binding.myreviewImageViewpager.adapter = myReviewImageViewPagerAdapter
        binding.myreviewImageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.myreviewImageViewpager.clipToPadding = false
        binding.myreviewImageViewpager.clipChildren = false
        binding.myreviewImageViewpager.offscreenPageLimit = 3

        binding.myreviewImageViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.myreviewImageCircleIndicator.selectDot(position)
                super.onPageSelected(position)
            }
        })

        binding.myreviewImageCircleIndicator.createDotPanel(imgs.size, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, 0)
    }

    override fun onResume() {
        super.onResume()
//      dialog fragment custom width
        try {
            val windowManager = requireContext().getSystemService(WINDOW_SERVICE) as WindowManager
            val display: Display = windowManager.defaultDisplay
            val deviceSize = Point()
            display.getSize(deviceSize)
            val params = dialog!!.window!!.attributes
            params.width = deviceSize.x
            params.horizontalMargin = 0.0f
            dialog!!.window!!.attributes = params
        } catch (e: Exception) {
            // regardless
            e.printStackTrace()
        }
    }

    private inner class MyReviewImageViewPagerAdapter(fragment: DialogFragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return imgs.size
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MyReviewImageFragment(imgs[0])
                1 -> MyReviewImageFragment(imgs[1])
                2 -> MyReviewImageFragment(imgs[2])
                3 -> MyReviewImageFragment(imgs[3])
                else -> MyReviewImageFragment(imgs[4])
            }
        }
    }
}