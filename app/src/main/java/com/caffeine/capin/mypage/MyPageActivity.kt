package com.caffeine.capin.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityMyPageBinding
import com.caffeine.capin.databinding.MyPageTabIconBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageBinding

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileEditButtonClickEvent()

        viewPager = binding.mypageViewpager

        var myPageViewPagerAdapter = MyPageViewPagerAdapter(this)
        binding.mypageViewpager.adapter = myPageViewPagerAdapter
        binding.mypageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.mypageTabLayout, viewPager) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()

    }

    fun getTabView(position: Int): View {
        val layoutInflater = LayoutInflater.from(this)
        val tabView = MyPageTabIconBinding.inflate(layoutInflater, null, false)
        val imageTab = tabView.mypageTabIcon

        when (position) {
            0 -> imageTab.setImageResource(R.drawable.selector_mypage_category_tab)
            else -> imageTab.setImageResource(R.drawable.selector_mypage_review_tab)
        }

        return tabView.root
    }

    private fun profileEditButtonClickEvent() {
        binding.mypageProfileEditBtn.setOnClickListener {
            val intent = Intent(this@MyPageActivity, MyPageProfileEditActivity::class.java)
            startActivity(intent)
        }
    }
}