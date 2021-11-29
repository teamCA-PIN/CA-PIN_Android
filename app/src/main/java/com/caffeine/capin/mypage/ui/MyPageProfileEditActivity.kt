package com.caffeine.capin.mypage.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.caffeine.capin.R
import com.caffeine.capin.cafeti.CafetiActivity
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.databinding.ActivityMyPageProfileEditBinding
import com.caffeine.capin.mypage.api.request.RequestProfileEditData
import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.review.write.ui.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MyPageProfileEditActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    private lateinit var binding: ActivityMyPageProfileEditBinding
    private lateinit var path: String

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            binding.profileEditProfileIv.setImageURI(result.data?.data)
            if(result.data != null) {
                path = absolutelyPath(result.data?.data!!)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMyInfoFromServer()
        setEditTextWatcher()

        binding.profileEditBackBtn.setOnClickListener { onBackPressed() }
        binding.profileEditProfileEditBtn.setOnClickListener {
            Log.d("리미", "편집 버튼 눌렀다")
            showEditProfileDialog()
        }
        binding.profileEditDoneBtn.setOnClickListener {
            Log.d("리미", "완료 버튼 눌렀다")
            putMyProfileEditToServer() }
    }

    private fun setEditTextWatcher() {
        binding.profileEditNameDeleteBtn.setOnClickListener {
            binding.profileEditNameEdt.text.clear()
            binding.profileEditNameDeleteBtn.isVisible = false
        }

        binding.profileEditNameEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.profileEditNameLengthTv.text = "0/10"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userInput = binding.profileEditNameEdt.text.toString()
                binding.profileEditNameLengthTv.text = "${userInput.length.toString()}/10"
                binding.profileEditNameDeleteBtn.isVisible = true
            }

            override fun afterTextChanged(s: Editable?) {
                val userInput = binding.profileEditNameEdt.text.toString()
                binding.profileEditNameLengthTv.text = "${userInput.length.toString()}/10"
                binding.profileEditNameDeleteBtn.isVisible = true
            }
        })
    }

    private fun showEditProfileDialog() {
        val profileEditList = ArrayList<CapinDialogButton>()
        val dialog: CapinDialog = CapinDialogBuilder("프로필 사진 설정")
            .setButtonArray(profileEditList)
            .setExitButton(true)
            .build()

        profileEditList.apply {
            add(
                CapinDialogButton("앨범에서 사진 선택",
                    ContextCompat.getColor(this@MyPageProfileEditActivity, R.color.maincolor_1),
                    this@MyPageProfileEditActivity,
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            chekGalleryPermission()
                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("기본 이미지로 변경",
                    ContextCompat.getColor(this@MyPageProfileEditActivity, R.color.maincolor_1),
                    this@MyPageProfileEditActivity,
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            getMyInfoFromServer()
                            dialog.dismiss()
                        }
                    })
            )
        }

        dialog.show(supportFragmentManager, "picture")
    }

    private fun getMyInfoFromServer() {
        val capinApiService = ServiceCreator.capinApiService.getMyInfo(
            userPreferenceManager.getUserToken()
        )

        capinApiService.enqueue(object : Callback<ResponseMyData> {
            override fun onFailure(call: Call<ResponseMyData>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(
                call: Call<ResponseMyData>,
                response: Response<ResponseMyData>
            ) {
                if (response.isSuccessful) {
                    val userName = response.body()?.myInfo?.nickname
                    binding.profileEditNameEdt.hint = userName
                    Glide
                        .with(binding.profileEditProfileIv.context)
                        .load(response.body()?.myInfo?.profileImg)
                        .into(binding.profileEditProfileIv)
                }
            }
        })
    }

    private fun chekGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Companion.PERMISSION_READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestGalleryPermission.launch(PERMISSION_READ_EXTERNAL_STORAGE)
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getImageLauncher.launch(intent)
        }
    }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                getImageLauncher.launch(intent)
            }
        }

    private fun putMyProfileEditToServer() {
        val file = File(path)

        Log.d("리미", file.name)

        var requestBody : RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("profileImg",file.name,requestBody)
        var nicknameBody : MultipartBody.Part = MultipartBody.Part.createFormData("nickname", binding.profileEditNameEdt.text.toString())

        Log.d("리미", requestBody.toString())
        Log.d("리미", body.toString())
        //todo 모르겠음 으앙아아ㅏㄱ 파일은 받았는데 그 뒤로는 모르게써~~~~~~

        val capinApiService = ServiceCreator.capinApiService.putMyProfileEdit(
            userPreferenceManager.getUserToken(),
            nickname = nicknameBody,
            profileImg = body
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    Log.d("리미", "멀티파트..")
                    finish()
                }
            }
        })
    }

    fun absolutelyPath(path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor = contentResolver.query(path, proj, null, null, null)!!
        var index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()

        var result = c.getString(index)

        return result
    }

    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}