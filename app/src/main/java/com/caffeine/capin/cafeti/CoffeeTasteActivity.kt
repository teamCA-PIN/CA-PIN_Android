package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityCoffeeTasteBinding

class CoffeeTasteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoffeeTasteBinding
    private var cafetiResultList = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeTasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("cafeti_result_list")) {
            cafetiResultList =intent.getSerializableExtra("cafeti_result_list") as ArrayList<Int>
            Log.d("CoffeeMostresult", "$cafetiResultList")
        }

        beforeButtonClickEvent()
        nextButtonClickEvent()
        updateCafetiImage()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CoffeeTasteActivity, CoffeeMostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateCafetiImage() {
        binding.radiogroupCoffeeTaste.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    binding.radiobtnSour.id -> {
                        binding.imageViewCoffeeTaste.setBackgroundResource(R.drawable.frame_120)
                    }
                    binding.radiobtnNosour.id -> {
                        binding.imageViewCoffeeTaste.setBackgroundResource(R.drawable.frame_119)
                    }
                    binding.radiobtnNoway.id -> {
                        binding.imageViewCoffeeTaste.setBackgroundResource(R.drawable.frame_118)
                    }
                }
            }
        })
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            val intent = Intent(this@CoffeeTasteActivity, CafeStyleActivity::class.java)
            val checkedButton = binding.radiogroupCoffeeTaste.checkedRadioButtonId
            if (checkedButton != -1) {
                when(checkedButton) {
                    binding.radiobtnSour.id -> {
                        cafetiResultList.add(0)
                        intent.putExtra("cafeti_result_list", cafetiResultList)
                        startActivity(intent)
                    }
                    binding.radiobtnNosour.id -> {
                        cafetiResultList.add(1)
                        intent.putExtra("cafeti_result_list", cafetiResultList)
                        startActivity(intent)
                    }
                    binding.radiobtnNoway.id -> {
                        cafetiResultList.add(2)
                        intent.putExtra("cafeti_result_list", cafetiResultList)
                        startActivity(intent)
                    }
                }
            }
            else {
                Toast.makeText(this@CoffeeTasteActivity,"한가지 항목을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}