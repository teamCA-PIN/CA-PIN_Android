package com.caffeine.capin.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivitySignupBinding
import com.caffeine.capin.login.*
import com.caffeine.capin.login.model.RequestSignUpData
import com.caffeine.capin.login.model.ResponseSignUpData
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.hideKeyBoard
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import javax.inject.Inject

import android.text.style.ForegroundColorSpan

import android.graphics.Color
import android.text.*

import android.widget.TextView
import android.text.SpannableString

import android.text.style.RelativeSizeSpan

import android.text.Spannable

import android.graphics.Typeface

import android.text.style.StyleSpan







@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    private lateinit var binding: ActivitySignupBinding
    private var edittextCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = findViewById<TextView>(R.id.textView)

        // 1
        val content = textView.text.toString()
        val spannableString = SpannableString(content)

        // 2

        val word = "서비스 이용약관"
        val start = content.indexOf(word)
        val end = start + word.length

        val word2 = "개인정보처리방침"
        val start2 = content.indexOf(word2)
        val end2 = start2 + word2.length


        // 3
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#000000")),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#000000")),
            start2,
            end2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        // 4
        textView.text = spannableString



        binding.btnSignup.clipToOutline = true

        binding.root.run {
            setOnClickListener { hideKeyBoard()}
        }
        signUpButtonClickEvent()
        imageviewButtonClickEvent()


        lateinit var username: EditText
        lateinit var email: EditText
        lateinit var password: EditText
        lateinit var newPassword: EditText
        username = binding.edittextName
        email = binding.edittextEmail
        password = binding.edittextPw
        newPassword = binding.edittextPwagain


        val edittextList = listOf<EditText>(
            username,
            email,
            password,
            newPassword
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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

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
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.pwDeleteBtn.isVisible = !s.isNullOrEmpty()
                checkEditTextEmpty()
            }
        })

        binding.emailidDeleteBtn.setOnClickListener {
            binding.emailidDeleteBtn.isVisible = false
        }

        binding.edittextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.emailidDeleteBtn.isVisible = !s.isNullOrEmpty()
                checkEditTextEmpty()
            }
        })

        binding.pwagainDeleteBtn.setOnClickListener {
            binding.pwagainDeleteBtn.isVisible = false
        }

        binding.edittextPwagain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.pwagainDeleteBtn.isVisible = !s.isNullOrEmpty()
                checkEditTextEmpty()
            }
        })


        binding.usernameDeleteBtn.setOnClickListener {
            binding.usernameDeleteBtn.isVisible = false
        }

        binding.edittextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.usernameDeleteBtn.isVisible = !s.isNullOrEmpty()
                checkEditTextEmpty()
            }
        })
    }


    private fun checkEditTextEmpty() {
        if (edittextCount >= 4) {
            binding.btnSignup.apply {
                setBackgroundColor(
                    ContextCompat.getColor(
                        this@SignUpActivity,
                        R.color.pointcolor_1
                    )
                )
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@SignUpActivity,
                        R.drawable.circle_image_view2
                    )
                )
                isClickable = true
            }
        } else {
            binding.btnSignup.apply {
                setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.gray_3))
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@SignUpActivity,
                        R.drawable.circle_image_view
                    )
                )
                isClickable = false
            }
        }
    }


    private fun signUpButtonClickEvent() {
        binding.btnSignup.setOnClickListener() {
            checkPassword()
        }
    }


    private fun checkPassword() {
        if (binding.edittextPw.text.toString() != binding.edittextPwagain.text.toString()) {
            CapinToastMessage.createCapinRejectToast(this@SignUpActivity, "비밀번호가 일치하지 않습니다.", 135)
                ?.show()
        } else {
            requestLogin()
        }
    }

    private fun requestLogin() {
        val requestSignUpData = RequestSignUpData(
            email = binding.edittextEmail.text.toString(),
            password = binding.edittextPw.text.toString(),
            nickname = binding.edittextName.text.toString()
        )
        if(requestSignUpData.email != null && requestSignUpData.password != null && requestSignUpData.nickname != null) {

            val pattern: Pattern = Patterns.EMAIL_ADDRESS

            //android.util 에서 제공하는 기본 패턴을 이용하면 정규식을 쓰지 않고 간단히 검사 가능
            if (pattern.matcher(requestSignUpData.email).matches()) {
                //이메일 맞는 형식


                //특수문자 검사

                val ps =
                    Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")
                if(requestSignUpData.nickname.length in 2..10
                    && ps.matcher(requestSignUpData.nickname).matches()) {


                    val call: Call<ResponseSignUpData> =
                        ServiceCreator.capinApiService.postSignUp(requestSignUpData)
                    call.enqueue(object : Callback<ResponseSignUpData> {
                        override fun onResponse(
                            call: Call<ResponseSignUpData>,
                            response: Response<ResponseSignUpData>
                        ) {
                            if (response.isSuccessful) {
                                userPreferenceManager.setNeedCafetiCheck(true)
                                CapinToastMessage.createCapinToast(
                                    this@SignUpActivity,
                                    "회원 가입 및 기본카테고리 생성 성공.",
                                    135
                                )

                                finish()

                            } else {
                                val errorBody = response.errorBody() ?: return
                                val errorMessage = JSONObject(errorBody.string())
                                // errorMessage -> 문자열 교환
                                CapinToastMessage.createCapinRejectToast(
                                    this@SignUpActivity,
                                    "이미 사용중인 이메일 입니다.", 135
                                )
                                    ?.show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
                            CapinToastMessage.createCapinRejectToast(
                                this@SignUpActivity,
                                "회원가입 실패.",
                                135)

                            Log.d("NetworkTest", "error:$t")
                        }
                    })
                }
                else
                {
                    CapinToastMessage.createCapinRejectToast(this@SignUpActivity, "닉네임이 형식에 맞지 않습니다. ", 135)

                }


            } else {
                CapinToastMessage.createCapinRejectToast(this@SignUpActivity, "이메일이 형식에 맞지 않습니다. ", 135)
                    ?.show()
            }

        }
        else
        {
            CapinToastMessage.createCapinRejectToast(this@SignUpActivity, "필요한 값이 없습니다.", 135)

        }
    }

    private fun imageviewButtonClickEvent() {
        binding.imageviewTool.setOnClickListener() {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}