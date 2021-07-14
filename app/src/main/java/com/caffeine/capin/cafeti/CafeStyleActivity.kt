package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityCafeStyleBinding
import com.caffeine.capin.databinding.ActivityCoffeeMostBinding

class CafeStyleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeStyleBinding
    private lateinit var coffeebinding: ActivityCoffeeMostBinding
    private var cafetiResultList = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("cafeti_result_list")) {
            cafetiResultList =intent.getSerializableExtra("cafeti_result_list") as ArrayList<Int>
            Log.d("CoffeeMenuresult", "$cafetiResultList")
        }

        beforeButtonClickEvent()
        nextButtonClickEvent()
        updateCafetiImage()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            when (coffeebinding.radiogroupCoffeeMost.checkedRadioButtonId) {
                coffeebinding.radiobtnCoffee.id -> {
                    val intent = Intent(this@CafeStyleActivity, CoffeeMostActivity::class.java)
                    startActivity(intent)
                }
                coffeebinding.radiobtnNoncoffee.id -> {
                    val intent = Intent(this@CafeStyleActivity, CoffeeMenuActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateCafetiImage() {
        binding.radiogroupCafeStyle.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    binding.radiobtnModern.id -> {
                        binding.imageViewCafeStyle.setBackgroundResource(R.drawable.frame_128)
                    }
                    binding.radiobtnVintage.id -> {
                        binding.imageViewCafeStyle.setBackgroundResource(R.drawable.frame_129)
                    }
                    binding.radiobtnHip.id -> {
                        binding.imageViewCafeStyle.setBackgroundResource(R.drawable.frame_130)
                    }
                    binding.radiobtnUnique.id -> {
                        binding.imageViewCafeStyle.setBackgroundResource(R.drawable.frame_131)
                    }
                    binding.radiobtnCute.id -> {
                        binding.imageViewCafeStyle.setBackgroundResource(R.drawable.frame_132)
                    }
                }
            }
        })
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
                val intent = Intent(this@CafeStyleActivity, CafeColorActivity::class.java)
                val checkedButton = binding.radiogroupCafeStyle.checkedRadioButtonId
                if (checkedButton != -1) {
                    when(checkedButton) {
                        binding.radiobtnModern.id -> {
                            cafetiResultList.add(0)
                            intent.putExtra("cafeti_result_list", cafetiResultList)
                            startActivity(intent)
                        }
                        binding.radiobtnVintage.id -> {
                            cafetiResultList.add(1)
                            intent.putExtra("cafeti_result_list", cafetiResultList)
                            startActivity(intent)
                        }
                        binding.radiobtnHip.id -> {
                            cafetiResultList.add(2)
                            intent.putExtra("cafeti_result_list", cafetiResultList)
                            startActivity(intent)
                        }
                        binding.radiobtnUnique.id -> {
                            cafetiResultList.add(3)
                            intent.putExtra("cafeti_result_list", cafetiResultList)
                            startActivity(intent)
                        }
                        binding.radiobtnCute.id -> {
                            cafetiResultList.add(4)
                            intent.putExtra("cafeti_result_list", cafetiResultList)
                            startActivity(intent)
                        }
                    }
                }
                else {
                    CapinToastMessage.createCapinRejectToast(this@CafeStyleActivity,"한가지 항목을 선택해주세요", 200)?.show()
                }
            }
        }
}
