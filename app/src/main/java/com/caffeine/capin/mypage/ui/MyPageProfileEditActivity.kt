package com.caffeine.capin.mypage.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityMyPageProfileEditBinding
import com.caffeine.capin.databinding.ActivityMyPageProfileEditBindingImpl
import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MyPageProfileEditActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    private lateinit var binding: ActivityMyPageProfileEditBinding
    private var path: String = ""
    private lateinit var tempBitmap: Bitmap
    private lateinit var nicknameBody: MultipartBody.Part
    private lateinit var profileImageBody: MultipartBody.Part

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            binding.profileEditProfileIv.setImageURI(result.data?.data)
            if (result.data != null) {
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
            showEditProfileDialog()
        }
        binding.profileEditDoneBtn.setOnClickListener {
            putMyProfileEditToServer()
        }
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
            userPreferenceManager.getUserAccessToken()
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

                    Glide.with(this@MyPageProfileEditActivity)
                        .asBitmap()
                        .load(response.body()?.myInfo?.profileImg)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                binding.profileEditProfileIv.setImageBitmap(resource)
                                tempBitmap = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                            }
                        })
                }
            }
        })
    }

    private fun chekGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                PERMISSION_READ_EXTERNAL_STORAGE
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
        nicknameBody = if (binding.profileEditNameEdt.text.isNullOrBlank()) {
            MultipartBody.Part.createFormData(
                "nickname",
                binding.profileEditNameEdt.hint.toString()
            )
        } else {
            MultipartBody.Part.createFormData(
                "nickname",
                binding.profileEditNameEdt.text.toString()
            )
        }

        if (path == "") {
            CapinToastMessage.createCapinRejectToast(
                this@MyPageProfileEditActivity,
                "프로필 이미지는 필수 입력 항목입니다.",
                100
            )?.show()
            return
            //fixme
//            Log.d("리미 bitmap", "init if")
//            val existingRequestBody : RequestBody = BitmapRequestBody(tempBitmap)
//            Log.d("리미 나한테 왜 그래 existing", existingRequestBody.toString())
//            profileImageBody = MultipartBody.Part.createFormData("profileImg", "", existingRequestBody)
//            Log.d("리미 나한테 왜 그래 profile", profileImageBody.toString())
        } else {
            val file = File(path)
            val requestBody : RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            profileImageBody = MultipartBody.Part.createFormData("profileImg", file.name, requestBody)
        }

        val capinApiService = ServiceCreator.capinApiService.putMyProfileEdit(
            userPreferenceManager.getUserAccessToken(),
            nickname = nicknameBody,
            profileImg = profileImageBody
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    CapinToastMessage.createCapinRejectToast(
                        this@MyPageProfileEditActivity,
                        "사용할 수 없는 이름입니다.",
                        100
                    )?.show()
                }
            }
        })
    }

    private fun absolutelyPath(path: Uri): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor = contentResolver.query(path, proj, null, null, null)!!
        val index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()

        return c.getString(index)
    }

    fun getFileFromBitmap(bitmap: Bitmap, context: Context): File? {
        val cache = context.externalCacheDir
        val shareFile = File(cache, "myFile.jpg")
        try {
            val out = FileOutputStream(shareFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            return shareFile
        } catch (e: Exception) {

        }
        return null
    }


    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType? = "image/jpeg".toMediaTypeOrNull()

        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }
}