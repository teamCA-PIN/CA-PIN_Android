package com.caffeine.capin.mypage.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.customview.CapinToastMessage
import com.caffeine.capin.databinding.ActivityMyPageProfileEditBinding
import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.bumptech.glide.load.engine.GlideException

import com.bumptech.glide.request.RequestListener

import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.lang.Exception
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.Drawable
import android.os.Environment
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.*


@AndroidEntryPoint
class MyPageProfileEditActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    private lateinit var binding: ActivityMyPageProfileEditBinding
    private var path: String? = ""
    private lateinit var profileImageBody: MultipartBody.Part
    private lateinit var nicknameBody: MultipartBody.Part
    private var tempNickname: String? = null
    private var tempCafeti: String? = null
    private var tempExistingImage: String? = null
    private var tempFile: File? = null

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
            makeMultipartBody()
            Handler(Looper.getMainLooper()).postDelayed({
                putMyProfileEditToServer()
            }, 1000)
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
                            nicknameBody =
                                MultipartBody.Part.createFormData("nickname", tempNickname!!)
                            Glide.with(this@MyPageProfileEditActivity)
                                .asBitmap().load(tempCafeti)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .listener(object : RequestListener<Bitmap?> {
                                    override fun onLoadFailed(
                                        @Nullable e: GlideException?,
                                        o: Any?,
                                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
                                        b: Boolean
                                    ): Boolean {
                                        return false
                                    }

                                    override fun onResourceReady(
                                        resource: Bitmap?,
                                        model: Any?,
                                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
                                        dataSource: DataSource?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        tempFile = bitmapToFile(resource!!, "image.jpeg")
                                        return true
                                    }
                                }
                                ).submit()
                            if (tempFile != null) {
                                val requestBody: RequestBody =
                                    tempFile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                                profileImageBody = MultipartBody.Part.createFormData(
                                    "profileImg",
                                    tempFile!!.name,
                                    requestBody
                                )
                            }
                            putMyProfileEditToServer()
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

                    tempNickname = response.body()?.myInfo?.nickname
                    tempCafeti = response.body()?.myInfo?.cafeti?.img
                    tempExistingImage = response.body()?.myInfo?.profileImg
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

    private fun makeMultipartBody() {
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
            Glide.with(this)
                .asBitmap()
                .load(tempExistingImage)
                .into(object: CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                        // 아래 resource가 들어간 뷰가 사라지는 등의 경우의 처리
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        // 얻어낸 Bitmap 자원을 resource를 통하여 접근.
                        tempFile = bitmapToFile(resource!!, "image.jpeg")
                    }
                })
            if (tempFile != null) {
                val requestBody: RequestBody =
                    tempFile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                profileImageBody =
                    MultipartBody.Part.createFormData("profileImg", tempFile!!.name, requestBody)
            }
        } else {
            val file = File(path)
            val requestBody: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            profileImageBody =
                MultipartBody.Part.createFormData("profileImg", file.name, requestBody)
        }
    }

    private fun putMyProfileEditToServer() {
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

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor = contentResolver.query(path, proj, null, null, null)!!
        var index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()

        var result = c.getString(index)

        return result
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.JPEG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}