package com.caffeine.capin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityLoginpwBinding


class LoginPwActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginpwBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginpwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFindpw.clipToOutline = true

        findPwButtonClickEvent()
    }

    private fun findPwButtonClickEvent() {
        binding.btnFindpw.setOnClickListener() {
            val loginemail = binding.findpwEdittextEmail.text
            if (loginemail.isNullOrBlank()) {
                Toast.makeText(this, "이메일을 찾을 수 없습니다.", LENGTH_SHORT).show()
            } else {
                //서버 통신
                val intent = Intent(this, FindPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
