package com.caffeine.capin.login.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityFindPasswordBinding
import com.caffeine.capin.login.model.RequestFindPwData
import com.caffeine.capin.login.model.RequestLoginPwData
import com.caffeine.capin.login.model.ResponseFindPwData
import com.caffeine.capin.login.model.ResponseLoginPwData
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.util.hideKeyBoard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.caffeine.capin.databinding.ActivityLoginpwBinding

class FindPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPasswordBinding
    private var edittextCount = 0

    private var Authnumber: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Authnumber = intent.getStringExtra("auth")
        binding.btnChange.clipToOutline = true
        binding.btnProve.clipToOutline = true

        binding.root.run {
            setOnClickListener { hideKeyBoard()}
        }
        numberButtonClickEvent()
        imageviewButtonClickEvent()
        changePwButtonClickEvent()

        lateinit var number : EditText
        lateinit var newPassword : EditText
        lateinit var newPasswordCheck : EditText
        number = binding.edittextNumber
        newPassword = binding.edittextNewpw
        newPasswordCheck = binding.edittextNewpwcheck

        val edittextList = listOf<EditText>(
            number,
            newPassword,
            newPasswordCheck
        )

        edittextList.forEach{ edittext ->
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
                    if(text.isNullOrEmpty()) {
                        edittextCount--
                        edittext.setTextColor(ContextCompat.getColor(this@FindPasswordActivity, R.color.gray_3))
                    } else {
                        edittextCount++
                        edittext.setTextColor(ContextCompat.getColor(this@FindPasswordActivity, R.color.black))
                    }
                }
            })
        }

        binding.pwDeleteBtn.setOnClickListener {
            binding.pwDeleteBtn.isVisible = false
        }

        binding.edittextNewpw.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.pwDeleteBtn.isVisible = !s.isNullOrEmpty()

            }
        })

        binding.pwagainDeleteBtn.setOnClickListener {
            binding.pwagainDeleteBtn.isVisible = false
        }

        binding.edittextNewpwcheck.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.pwagainDeleteBtn.isVisible = !s.isNullOrEmpty()
                binding.btnChange.apply {
                    setBackgroundDrawable(ContextCompat.getDrawable(this@FindPasswordActivity,
                        R.drawable.circle_image_view2
                    ))
                    isClickable = true
                }

            }
        })

    }

    private fun changePwButtonClickEvent() {
        binding.btnChange.setOnClickListener() {
            val password = binding.edittextNewpw.text
            val newPassword = binding.edittextNewpwcheck.text
            if (password.isNullOrBlank() || newPassword.isNullOrBlank()) {

            }
            else {
                val intent = Intent(this@FindPasswordActivity, LoginActivity::class.java)
                CapinToastMessage.createCapinToast(this@FindPasswordActivity,"비밀번호가 변경되었습니다.", 135)?.show()
                startActivity(intent)
            }
        }
    }




    private fun numberButtonClickEvent() {
        binding.btnProve.setOnClickListener() {
            val authNumber = binding.edittextNumber.text
            if (authNumber.toString() != Authnumber){
                CapinToastMessage.createCapinRejectToast(this@FindPasswordActivity,"인증번호가 틀립니다.",135)

            }
            else {
                CapinToastMessage.createCapinToast(this@FindPasswordActivity,"인증되었습니다.",135)?.show()
            }
        }
    }




    private fun imageviewButtonClickEvent(){
        binding.imageviewTool.setOnClickListener() {
            val intent = Intent(this@FindPasswordActivity, LoginPwActivity::class.java)
            startActivity(intent)
        }
    }


}