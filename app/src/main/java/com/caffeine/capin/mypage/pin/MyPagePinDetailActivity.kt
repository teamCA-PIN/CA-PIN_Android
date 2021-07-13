package com.caffeine.capin.mypage.pin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.DialogClickListener
import com.caffeine.capin.databinding.ActivityMyPagePinDetailBinding

class MyPagePinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPagePinDetailBinding
    private lateinit var myPinInfoAdapter: MyPinInfoAdapter
    private var removePinInfoList: MutableList<MyPinInfo> = mutableListOf()

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

        binding.pinDetailEditBtn.setOnClickListener {
            myPinInfoAdapter.switchDeleteMode(true)
            binding.pinDetailEditBtn.isGone = true
            binding.pinDetailDeleteBtn.isVisible = true
        }

        binding.pinDetailDeleteBtn.setOnClickListener {
            showDeletePinConfirmDialog()
            binding.pinDetailEditBtn.isVisible = true
            binding.pinDetailDeleteBtn.isGone = true
        }

        myPinInfoAdapter = MyPinInfoAdapter()
        binding.pinDetailRcv.adapter = myPinInfoAdapter

        myPinInfoAdapter.setOnCheckboxClickListener(object :
            MyPinInfoAdapter.OnCheckboxClickListener{
            override fun onCheckboxClick(myPinInfo: MyPinInfo) {
                if(myPinInfo in removePinInfoList) {
                    removePinInfoList.remove(myPinInfo)
                    if (removePinInfoList.size > 0) {
                        binding.pinDetailHeaderTv.text = "${removePinInfoList.size}개 선택됨"
                    } else {
                        myPinInfoAdapter.switchDeleteMode(false)
                        binding.pinDetailEditBtn.isVisible = true
                        binding.pinDetailDeleteBtn.isGone = true
                        binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
                    }
                } else {
                    removePinInfoList.add(myPinInfo)
                    binding.pinDetailHeaderTv.text = "${removePinInfoList.size}개 선택됨"
                }
            }
        })

        val decoration = DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        binding.pinDetailRcv.addItemDecoration(decoration)

        myPinInfoAdapter.myPinInfoList.addAll(
            listOf<MyPinInfo>(
                MyPinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집")
                    )
                ),
                MyPinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집")
                    )
                ),
                MyPinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페")
                    )
                ),
                MyPinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                ),
                MyPinInfo(
                    name = "후엘고",
                    address = "서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길어지면 여기까지 내려올 수 있다~서울 마포구 마포대로11길 118 1층 (염리동) 주소가 길...",
                    tags = listOf(
                        PinInfoTag("카페맛집"),
                        PinInfoTag("디저트맛집"),
                        PinInfoTag("브런치카페"),
                        PinInfoTag("태그태그")
                    )
                ),
                MyPinInfo(
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
                MyPinInfo(
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
                MyPinInfo(
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
        myPinInfoAdapter.notifyDataSetChanged()
        binding.pinDetailNumTv.setText("총 ${myPinInfoAdapter.myPinInfoList.size}개의 핀")

        if(myPinInfoAdapter.myPinInfoList.size > 1) {
            binding.pinDetailBasicIv.isVisible = false
            binding.pinDetailBasicTv.isVisible = false
        }
    }

    private fun showDeletePinConfirmDialog() {
        val dialog: CapinDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("선택된 핀을 모두 삭제하시겠습니까?")
            .setContentDialogButtons(true, object: DialogClickListener {
                override fun onClick() {
                    for (i in 0 until removePinInfoList.size) {
                        myPinInfoAdapter.myPinInfoList.remove(removePinInfoList[i])
                        myPinInfoAdapter.checkboxList.forEach{
                            it.isChecked = false
                        }
                    }
                    myPinInfoAdapter.checkboxList.clear()

                    removePinInfoList.clear()
                    myPinInfoAdapter.switchDeleteMode(false)
                    myPinInfoAdapter.notifyDataSetChanged()
                    binding.pinDetailNumTv.setText("총 ${myPinInfoAdapter.myPinInfoList.size}개의 핀")
                    binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
                }
            }).build()
        dialog.show(supportFragmentManager, "DeleteReview")
    }
}