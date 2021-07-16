package com.caffeine.capin.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityMyPageBinding
import com.caffeine.capin.databinding.MyPageTabIconBinding
import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageBinding
    private lateinit var viewPager: ViewPager2
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()
        getMyInfoFromServer()
        profileEditButtonClickEvent()
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

    private fun getMyInfoFromServer() {
        val capinApiService = ServiceCreator.capinApiService.getMyInfo(
            userPreferenceManager.getUserToken()
        )

        capinApiService.enqueue(object : Callback<ResponseMyData> {
            override fun onFailure(call: Call<ResponseMyData>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(
                call: Call<ResponseMyData>,
                response: Response<ResponseMyData>
            ) {
                if (response.isSuccessful) {
                    binding.mypageUsernameTv.setText(response.body()?.myInfo?.nickname as String)
                    binding.mypageCafetiTv.setText(response.body()?.myInfo?.cafeti?.type as String)

                    Glide
                        .with(binding.mypageProfileIv.context)
                        .load(response.body()?.myInfo?.profileImg)
                        .into(binding.mypageProfileIv)

                }
            }
        })
    }

    private fun setViewPager() {
        viewPager = binding.mypageViewpager

        var myPageViewPagerAdapter = MyPageViewPagerAdapter(this)
        binding.mypageViewpager.adapter = myPageViewPagerAdapter
        binding.mypageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.mypageTabLayout, viewPager) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()

        binding.mypageCloseBtn.setOnClickListener { finish() }
    }
}