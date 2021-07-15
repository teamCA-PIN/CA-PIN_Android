package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.caffeine.capin.R
import com.caffeine.capin.ServiceCreator
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityCafeColorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CafeColorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeColorBinding
    private var cafetiResultList = arrayListOf<Int>()
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MGViMTAwMTJkNGNhZDY0ZjBkNmVhMDAiLCJpYXQiOjE2MjYyODM2NTksImV4cCI6MTYyNjM3MDA1OX0.91T1wDbDKyJY3rDOMKUXsTmFKUnNxdaM1yOYRyW72U0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("cafeti_result_list")) {
            cafetiResultList = intent.getSerializableExtra("cafeti_result_list") as ArrayList<Int>
            Log.d("CafeStyleresult", "$cafetiResultList")
        }

        beforeButtonClickEvent()
        nextButtonClickEvent()
        updateCafetiImage()

        getAPI()
    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CafeColorActivity, CafeStyleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateCafetiImage() {
        binding.radiogroupCafeColor.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    binding.radiobtnDark.id -> {
                        binding.imageViewCafeColor.setBackgroundResource(R.drawable.frame_124)
                    }
                    binding.radiobtnLight.id -> {
                        binding.imageViewCafeColor.setBackgroundResource(R.drawable.frame_125)
                    }
                    binding.radiobtnWarm.id -> {
                        binding.imageViewCafeColor.setBackgroundResource(R.drawable.frame_126)
                    }
                    binding.radiobtnColorful.id -> {
                        binding.imageViewCafeColor.setBackgroundResource(R.drawable.frame_127)
                    }

                }
            }
        })
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            val checkedButton = binding.radiogroupCafeColor.checkedRadioButtonId
            if (checkedButton != -1) {
                when (checkedButton) {
                    binding.radiobtnDark.id -> {
                        cafetiResultList.add(0)
                        getAPI()
                    }
                    binding.radiobtnLight.id -> {
                        cafetiResultList.add(1)
                        getAPI()
                    }
                    binding.radiobtnWarm.id -> {
                        cafetiResultList.add(2)
                        getAPI()
                    }
                    binding.radiobtnColorful.id -> {
                        cafetiResultList.add(3)
                        getAPI()
                    }
                }
            } else {
                CapinToastMessage.createCapinRejectToast(this@CafeColorActivity, "한가지 항목을 선택해주세요", 135)?.show()
            }
        }
    }

    private fun getAPI() {
        val requestCafetiData = RequestCafetiData(
            answers = cafetiResultList
        )
        val call: Call<ResponseCafetiData> =
            ServiceCreator.capinService.postCafeti(
                token,
                requestCafetiData
            )
        call.enqueue(object : Callback<ResponseCafetiData> {
            override fun onResponse(
                call: Call<ResponseCafetiData>,
                response: Response<ResponseCafetiData>
            ) {
                if (response.isSuccessful) {
                    Log.e("dfsd", "dfasdf")
                    Toast.makeText(this@CafeColorActivity, "CAFETI 검사 완료", Toast.LENGTH_SHORT)
                    val intent = Intent(this@CafeColorActivity, CafetiResultActivity::class.java)
                    intent.putExtra("cafeti_result", response.body()?.result)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@CafeColorActivity, "필요한 값이 없습니다.", Toast.LENGTH_SHORT)
                }
            }

            override fun onFailure(call: Call<ResponseCafetiData>, t: Throwable) {
                Log.d("CafeColorNetworkTest", "error:$t")
            }
        })

    }

    }
