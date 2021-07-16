package com.caffeine.capin.cafeti.ui

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.R
import com.caffeine.capin.cafeti.CafetiActivity
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityCoffeeMostBinding


class CoffeeMostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoffeeMostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeMostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()
        updateCafetiImage()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CoffeeMostActivity, CafetiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateCafetiImage() {
        binding.radiogroupCoffeeMost.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    binding.radiobtnCoffee.id -> {
                        binding.imageviewCoffeeMost.setBackgroundResource(R.drawable.coffee)
                    }
                    binding.radiobtnNoncoffee.id -> {
                        binding.imageviewCoffeeMost.setBackgroundResource(R.drawable.non_coffee)
                    }
                }
            }
        })
    }

    private fun nextButtonClickEvent() {
        val checkedButtonList = arrayListOf<Int>()
        binding.btnNext.setOnClickListener() {

            val checkedButton = binding.radiogroupCoffeeMost.checkedRadioButtonId
            if (checkedButton != -1) {
                when(checkedButton) {
                    binding.radiobtnCoffee.id -> {
                        checkedButtonList.add(0)
                        val intent = Intent(this@CoffeeMostActivity, CoffeeTasteActivity::class.java)
                        intent.putExtra("cafeti_result_list", checkedButtonList)
                        startActivity(intent)
                    }
                    binding.radiobtnNoncoffee.id -> {
                        checkedButtonList.add(1)
                        val intent = Intent(this@CoffeeMostActivity, CoffeeMenuActivity::class.java)
                        intent.putExtra("cafeti_result_list", checkedButtonList)
                        startActivity(intent)
                    }
                }
            }
            else {
                CapinToastMessage.createCapinRejectToast(this@CoffeeMostActivity,"한가지 항목을 선택해주세요", 135)?.show()
            }
        }
    }
}