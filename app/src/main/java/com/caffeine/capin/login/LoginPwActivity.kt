package com.caffeine.capin.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.ServiceCreator
import com.caffeine.capin.databinding.ActivityLoginpwBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPwActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginpwBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginpwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFindpw.clipToOutline = true


        binding.loginpwDeleteBtn.setOnClickListener {
            binding.loginpwDeleteBtn.isVisible = false
        }

        binding.loginpwEdittextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.loginpwDeleteBtn.isVisible = !s.isNullOrEmpty()
                checkEditTextEmpty()

            }
        })


        findPwButtonClickEvent()

    }

    private fun checkEditTextEmpty() {
        if (binding.loginpwEdittextEmail.text.toString().isNotEmpty()) {
            binding.btnFindpw.apply {
                setBackgroundDrawable(ContextCompat.getDrawable(this@LoginPwActivity,
                    R.drawable.circle_image_view2
                ))
                isClickable = true
            }
        } else {
            binding.btnFindpw.apply {
                setBackgroundDrawable(ContextCompat.getDrawable(this@LoginPwActivity,
                    R.drawable.circle_image_view
                ))
                isClickable = false
            }
        }
    }

    private fun findPwButtonClickEvent() {
        binding.btnFindpw.setOnClickListener() {
            val requestLoginPwData = RequestLoginPwData(
                email = binding.loginpwEdittextEmail.text.toString()
            )
            val call: Call<ResponseLoginPwData> = ServiceCreator.capinService.postLoginPw(requestLoginPwData)
            call.enqueue(object : Callback<ResponseLoginPwData> {
                override fun onResponse(
                    call: Call<ResponseLoginPwData>,
                    response: Response<ResponseLoginPwData>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginPwActivity, "이메일 인증번호 전송 성공", LENGTH_SHORT)
                        intent = Intent(this@LoginPwActivity, FindPasswordActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginPwActivity, "이메일을 찾을 수 없습니다.", LENGTH_SHORT).show()
                        binding.loginpwEdittextEmail.setTextColor(ContextCompat.getColor(this@LoginPwActivity,R.color.pointcolor_red))
                    }
                }

                override fun onFailure(call: Call<ResponseLoginPwData>, t: Throwable) {
                    Log.d("NetworkTest", "error:$t")
                }
            })
        }
    }
}