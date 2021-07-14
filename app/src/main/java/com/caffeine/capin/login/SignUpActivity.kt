package com.caffeine.capin.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.ServiceCreator.capinService
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivitySignupBinding
import com.caffeine.capin.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var edittextCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignup.clipToOutline = true

        signUpButtonClickEvent()

        lateinit var username: EditText
        lateinit var email: EditText
        lateinit var password: EditText
        lateinit var newpassword: EditText
        username = binding.edittextName
        email = binding.edittextEmail
        password = binding.edittextPw
        newpassword = binding.edittextPwagain


        val edittextList = listOf<EditText>(
            username,
            email,
            password,
            newpassword
        )

        edittextList.forEach { edittext ->
            edittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    if (text.isNullOrEmpty()) {
                        edittextCount--
                        edittext.setTextColor(
                            ContextCompat.getColor(
                                this@SignUpActivity,
                                R.color.gray_3
                            )
                        )
                    } else {
                        edittextCount++
                        edittext.setTextColor(
                            ContextCompat.getColor(
                                this@SignUpActivity,
                                R.color.black
                            )
                        )
                    }
                    checkEditTextEmpty()
                }
            })
        }

        binding.pwDeleteBtn.setOnClickListener {
            binding.pwDeleteBtn.isVisible = false
        }

        binding.edittextPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.pwDeleteBtn.isVisible = !s.isNullOrEmpty()

            }
        })

        binding.emailidDeleteBtn.setOnClickListener {
            binding.emailidDeleteBtn.isVisible = false
        }

        binding.edittextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.emailidDeleteBtn.isVisible = !s.isNullOrEmpty()

            }
        })

        binding.pwagainDeleteBtn.setOnClickListener {
            binding.pwagainDeleteBtn.isVisible = false
        }

        binding.edittextPwagain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.pwagainDeleteBtn.isVisible = !s.isNullOrEmpty()

            }
        })

        binding.usernameDeleteBtn.setOnClickListener {
            binding.usernameDeleteBtn.isVisible = false
        }

        binding.edittextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.usernameDeleteBtn.isVisible = !s.isNullOrEmpty()

            }
        })


    }

    private fun checkEditTextEmpty() {
        if (edittextCount >= 4) {
            binding.btnSignup.apply {
                setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.pointcolor_1))
                setBackgroundDrawable(ContextCompat.getDrawable(this@SignUpActivity,
                    R.drawable.circle_image_view2
                ))
                isClickable = true
            }
        } else {
            binding.btnSignup.apply {
                setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.gray_3))
                setBackgroundDrawable(ContextCompat.getDrawable(this@SignUpActivity,
                    R.drawable.circle_image_view
                ))
                isClickable = false
            }
        }
    }


    private fun signUpButtonClickEvent() {
        binding.btnSignup.setOnClickListener() {
            val requestSignUpData = RequestSignUpData(
                email = binding.edittextEmail.text.toString(),
                password = binding.edittextPw.text.toString(),
                nickname = binding.edittextName.text.toString()
            )
            val call: Call<ResponseSignUpData> = capinService.postSignUp(requestSignUpData)
            call.enqueue(object : Callback<ResponseSignUpData> {
                override fun onResponse(
                    call: Call<ResponseSignUpData>,
                    response: Response<ResponseSignUpData>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUpActivity,"가입이 완료되었습니다.", LENGTH_SHORT).show()
                        intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {

                        CapinToastMessage.createCapinRejectToast(this@SignUpActivity, "비밀번호가 일치하지 않습니다.",  200)?.show()
                    }
                }

                override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
                    CapinToastMessage.createCapinRejectToast(this@SignUpActivity, "비밀번호가 일치하지 않습니다.",  200)?.show()
                    Log.d("NetworkTest", "error:$t")
                }
            })

        }
    }



}