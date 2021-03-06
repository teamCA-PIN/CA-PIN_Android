package com.caffeine.capin.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.caffeine.capin.presentation.main.MainActivity
import com.caffeine.capin.R
import com.caffeine.capin.presentation.cafeti.ui.CafetiActivity
import com.caffeine.capin.databinding.ActivityMyPageBinding
import com.caffeine.capin.databinding.MyPageTabIconBinding
import com.caffeine.capin.data.dto.mypage.ResponseMyData
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.data.local.UserPreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        setButtonClickEvent()
        getMyInfoFromServer()
    }

    override fun onResume() {
        super.onResume()
        getMyInfoFromServer()
    }

    private fun getTabView(position: Int): View {
        val layoutInflater = LayoutInflater.from(this)
        val tabView = MyPageTabIconBinding.inflate(layoutInflater, null, false)
        val imageTab = tabView.mypageTabIcon

        when (position) {
            0 -> imageTab.setImageResource(R.drawable.selector_mypage_category_tab)
            else -> imageTab.setImageResource(R.drawable.selector_mypage_review_tab)
        }

        return tabView.root
    }

    private fun getMyInfoFromServer() {
        val capinApiService = ServiceCreator.capinApiService.getMyInfo(
            userPreferenceManager.getUserAccessToken()
        )

        capinApiService.enqueue(object : Callback<ResponseMyData> {
            override fun onFailure(call: Call<ResponseMyData>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ResponseMyData>,
                response: Response<ResponseMyData>
            ) {
                if (response.isSuccessful) {
                    response.body()?.myInfo?.let {
                        binding.mypageUsernameTv.text = it.nickname
                        binding.mypageCafetiTv.text = it.cafeti.type
                        binding.mypageProfileIv.run {
                            if (it.profileImg.isNullOrEmpty()) {
                                setBackgroundColor(ContextCompat.getColor(this@MyPageActivity, R.color.gray_3))
                            } else {
                                Glide.with(this.context)
                                    .load(response.body()?.myInfo?.profileImg)
                                    .into(this)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setButtonClickEvent() {
        binding.mypageCafetiBtnTv.setOnClickListener {
            val intent = Intent(this, CafetiActivity::class.java)
            intent.putExtra(FROM_MYPAGE, true)
            startActivity(intent)
        }

        binding.mypageProfileEditBtnTv.setOnClickListener {
            val intent = Intent(this, MyPageProfileEditActivity::class.java)
            startActivity(intent)
        }

        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                popBackStack()
            }
        })

        binding.mypageBackBtn.setOnClickListener { popBackStack() }
    }

    private fun popBackStack() {
        val intent = Intent(this@MyPageActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        finish()
    }


    private fun setViewPager() {
        viewPager = binding.mypageViewpager

        var myPageViewPagerAdapter = MyPageViewPagerAdapter(this)
        binding.mypageViewpager.adapter = myPageViewPagerAdapter
        binding.mypageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.mypageTabLayout, viewPager) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()
    }

    companion object {
        const val FROM_MYPAGE = "FROM_MYPAGE"
    }
}