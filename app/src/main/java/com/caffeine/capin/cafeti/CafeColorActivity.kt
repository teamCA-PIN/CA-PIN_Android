package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafeColorBinding

class CafeColorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCafeColorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CafeColorActivity, CafeStyleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            Log.e("button", "${binding.radiogroupCafeColor.checkedRadioButtonId}")
            if (binding.radiogroupCafeColor.checkedRadioButtonId != -1) {
                //Todo: 서버통신 연결 작업
                    val requestCafetiData = RequestCafetiData(
                        answers = binding.
                    )
                val intent = Intent(this@CafeColorActivity, CafetiResultActivity::class.java)
                startActivity(intent)
            }
            else{

            }
        }
    }
}