package com.caffeine.capin.mypage.pin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.capin.databinding.ActivityMyPagePinDetailBinding

class MyPagePinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPagePinDetailBinding
    private lateinit var pinInfoAdapter: PinInfoAdapter
    private var removeItemInfo: MutableList<PinInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("name")) {
            binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
        } else {
            binding.pinDetailHeaderTv.text = "카테고리"
        }

        binding.pinDetailBackBtn.setOnClickListener { onBackPressed() }

        binding.pinDetailDeleteCheckbox.setOnClickListener {
            pinInfoAdapter.switchDeleteMode(binding.pinDetailDeleteCheckbox.isChecked)
        }

        pinInfoAdapter = PinInfoAdapter()
        binding.pinDetailRcv.adapter = pinInfoAdapter

        pinInfoAdapter.setOnCheckboxClickListener(object :
            PinInfoAdapter.OnCheckboxClickListener{
            override fun onCheckboxClick(pinInfo: PinInfo) {
                if(pinInfo in removeItemInfo) {
                    removeItemInfo.remove(pinInfo)
                    binding.pinDetailHeaderTv.text = "${removeItemInfo.size}개 선택됨"
                } else {
                    removeItemInfo.add(pinInfo)
                    binding.pinDetailHeaderTv.text = "${removeItemInfo.size}개 선택됨"
                }
            }
        })

        val decoration = DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        binding.pinDetailRcv.addItemDecoration(decoration)

        pinInfoAdapter.pinInfoList.addAll(
            listOf<PinInfo>(
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그"),
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그"),
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                ),
                PinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그"),
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                )
            )
        )
        pinInfoAdapter.notifyDataSetChanged()
    }
}