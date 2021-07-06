package com.caffeine.capin.mypage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityMyPageBinding
import com.caffeine.capin.databinding.MyPageTabIconBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.my_page_tab_icon, null, false)
//        val ivTab = view.findViewById<ImageView>(R.id.mypage_tab_icon)
        when (position) {
            0 -> imageTab.setImageResource(R.drawable.mypage_category_tab_selector)
            else -> imageTab.setImageResource(R.drawable.mypage_review_tab_selector)
        }

        return tabView.root
    }
}