package com.caffeine.capin.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.presentation.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityLoginpwBinding
import com.caffeine.capin.data.dto.login.RequestLoginPwData
import com.caffeine.capin.data.dto.login.ResponseLoginPwData
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.util.hideKeyBoard
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

        imageviewButtonClickEvent()
        binding.root.run {
            setOnClickListener { hideKeyBoard()}
        }

        binding.loginpwDeleteBtn.setOnClickListener {
            binding.loginpwEdittextEmail.text?.clear()
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
            val call: Call<ResponseLoginPwData> = ServiceCreator.capinApiService.postLoginPw(requestLoginPwData)
            call.enqueue(object : Callback<ResponseLoginPwData> {
                override fun onResponse(
                    call: Call<ResponseLoginPwData>,
                    response: Response<ResponseLoginPwData>
                ) {
                    if (response.isSuccessful) {
                        CapinToastMessage.createCapinToast(
                            this@LoginPwActivity,
                            "??????????????? ?????????????????????.",
                            135
                        )?.show()
                        intent = Intent(this@LoginPwActivity, FindPasswordActivity::class.java)
                        intent.putExtra("auth",response.body()?.auth.toString())
                        startActivity(intent)
                    } else {
                        CapinToastMessage.createCapinRejectToast(
                            this@LoginPwActivity,
                            "???????????? ?????? ??? ????????????.",
                            135
                        )?.show()
                        binding.loginpwEdittextEmail.setTextColor(ContextCompat.getColor(this@LoginPwActivity,R.color.pointcolor_red))
                    }
                }

                override fun onFailure(call: Call<ResponseLoginPwData>, t: Throwable) {
                    Log.d("NetworkTest", "error:$t")
                }
            })
        }
    }

    private fun imageviewButtonClickEvent(){
        binding.imageviewBackButton.setOnClickListener() {
            val intent = Intent(this@LoginPwActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
