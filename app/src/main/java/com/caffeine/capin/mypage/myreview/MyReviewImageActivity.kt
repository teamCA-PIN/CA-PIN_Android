package com.caffeine.capin.mypage.myreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.caffeine.capin.databinding.ActivityMyReviewImageBinding

class MyReviewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyReviewImageBinding
    private lateinit var myReviewImageViewPagerAdapter: MyReviewImageViewPagerAdapter

    var imgs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgs = intent.getStringArrayListExtra("imgs")!!

        myReviewImageViewPagerAdapter = MyReviewImageViewPagerAdapter(this)
        binding.myreviewImageViewpager.adapter = myReviewImageViewPagerAdapter
        binding.myreviewImageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    private inner class MyReviewImageViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
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