package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityCoffeeMenuBinding

class CoffeeMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoffeeMenuBinding
    private var cafetiResultList = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("cafeti_result_list")) {
            cafetiResultList =intent.getSerializableExtra("cafeti_result_list") as ArrayList<Int>
            Log.d("CoffeeTasteresult", "$cafetiResultList")
        }

        beforeButtonClickEvent()
        nextButtonClickEvent()
        updateCafetiImage()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CoffeeMenuActivity, CoffeeMostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateCafetiImage() {
        binding.radiogroupCoffeeMenu.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    binding.radiobtnTea.id -> {
                        binding.imageViewCoffeeMenu.setBackgroundResource(R.drawable.frame_121)
                    }
                    binding.radiobtnLatte.id -> {
                        binding.imageViewCoffeeMenu.setBackgroundResource(R.drawable.frame_122)
                    }
                    binding.radiobtnJuiceade.id -> {
                        binding.imageViewCoffeeMenu.setBackgroundResource(R.drawable.frame_123)
                    }
                }
            }
        })
    }


    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            val intent = Intent(this@CoffeeMenuActivity, CafeStyleActivity::class.java)

            val checkedButton = binding.radiogroupCoffeeMenu.checkedRadioButtonId
            if (checkedButton != -1) {
                when(checkedButton) {
                    binding.radiobtnTea.id -> {
                        cafetiResultList.add(0)
                        intent.putExtra("cafeti_result_list", cafetiResultList)
                        startActivity(intent)
                    }
                    binding.radiobtnLatte.id -> {
                        cafetiResultList.add(1)
                        intent.putExtra("cafeti_result_list", cafetiResultList)
                        startActivity(intent)
                    }
                    binding.radiobtnJuiceade.id -> {
                        cafetiResultList.add(2)
                        intent.putExtra("cafeti_result_list", cafetiResultList)
                        startActivity(intent)
                    }
                }
            }
            else {
                CapinToastMessage.createCapinRejectToast(this@CoffeeMenuActivity,"한가지 항목을 선택해주세요", 135)?.show()
            }
        }
    }
}