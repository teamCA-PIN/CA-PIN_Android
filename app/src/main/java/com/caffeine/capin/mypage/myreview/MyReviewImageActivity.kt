package com.caffeine.capin.mypage.myreview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.caffeine.capin.databinding.ActivityMyReviewImageBinding
import com.caffeine.capin.mypage.MyPageViewPagerAdapter

class MyReviewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyReviewImageBinding
    private lateinit var myReviewImageViewPagerAdapter: MyReviewImageViewPagerAdapter

    var imgs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgs = intent.getStringArrayListExtra("imgs")!!
        Log.d("리미 이거 확인해", imgs.toString())

        myReviewImageViewPagerAdapter = MyReviewImageViewPagerAdapter(this)
        binding.myreviewImageViewpager.adapter = myReviewImageViewPagerAdapter
        binding.myreviewImageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    private inner class MyReviewImageViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            Log.d("리미야 확인", imgs.size.toString())
            return imgs.size
        }

        override fun createFragment(position: Int): Fragment {
            Log.d("리미야 확인", "프래그먼트 생기긴 하니")
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