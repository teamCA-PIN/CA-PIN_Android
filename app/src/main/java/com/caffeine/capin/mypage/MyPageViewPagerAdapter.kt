package com.caffeine.capin.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPageViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val TYPE_CATEGORY = 0
    private val TYPE_REVIEW = 1
    private var listType: List<Int> = listOf(TYPE_CATEGORY, TYPE_REVIEW)

    override fun getItemCount(): Int {
        return listType.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TYPE_CATEGORY -> MyPageCategoryFragment()
            else -> MyPageReviewFragment()
        }
    }
}