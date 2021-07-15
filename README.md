# 믿고 보는 카페 맵, C A : P I N ☕
<img src="https://user-images.githubusercontent.com/76424700/125826222-b59ef45c-826b-467a-92d1-e1a63be33111.png" width="700" height="400"/>


## 📌 Project Structure
🌠 Capin_android  
📂 cafeti  
 ┣ 📂 CafeColorActivity  
 ┣ 📂 CafeStyleActivity  
 ┣ 📂 CoffeeMostActivity  
 ┣ 📂 CoffeeTasteActivity  
 ┣ 📂 CoffeeMenuActivity  
 ┣ 📂 CafetiActivity  
 ┣ 📂 CafetiResultActivity  
 ┣ 📂 RequestCafetiData    
 ┣ 📂 ResponseCafetiData  
📂 category  
 ┣ 📂 CategoryListAdapter  
 ┣ 📂 CategiryNameEntity  
 ┣ 📂 CategoryViewModel  
 ┣ 📂 SelectCategoryActivity      
📂 customview  
 ┣ 📂 CapinActiveInActiveButton    
 ┣ 📂 CapinChip    
 ┣ 📂 CapinDialog    
 ┣ 📂 CapinDialogBuilder  
 ┣ 📂 CapinDialogButton  
 ┣ 📂 CapinMapToolbar  
 ┣ 📂 CapinToastMessage  
 ┣ 📂 CapinToolbar    
 ┣ 📂 DialogClickListener   
 ┣ 📂 TagFilterCheckbox  
📂 detail        
  ┣ 📂 menus   
    ┣┣ 📂 CafeMenu    
    ┣┣ 📂 CafeMenusActivity      
    ┣┣ 📂 CafeMenusAdapter     
    ┣┣ 📂 CafeMenusViewModel  
  

📂 di    
📂 login   
📂 map  
📂 mypage  
📂 network  
📂 preference  
📂 profile  
📂 signup  
📂 tagfilter  
📂 util  
   ┣ 📂 CapinApplication    
   ┃ ┣ 📂 CapinService  
   ┃ ┗ 📂 ServiceCreator   
   ┣ 📂 PictureUriEntity  
   ┃ ┗ 📂 MainActivity 
  
  
    
## 🔗 Dependency
``` C
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        implementation(Dependencies.KOTLIN)
        implementation(Dependencies.CORE_KTX)
        implementation(Dependencies.APPCOMPAT)
        implementation(Dependencies.MATERIAL)
        implementation(Dependencies.CONSTRAIN_LAYOUT)
        implementation(Dependencies.CARD_VIEW)
        implementation(Dependencies.VIEW_PAGER)
        implementation(Dependencies.RECYCLER_VIEW)

        implementation(Dependencies.FRAGMENT_KTX)
        implementation(Dependencies.LOTTIE)

        implementation(Dependencies.GLIDE)
        kapt(Dependencies.GLIDE_COMPILER)

        implementation(Dependencies.RETROFIT)
        implementation(Dependencies.RETROFIT_CONVERTER)
        implementation(Dependencies.RETROFIT_RX_ADAPTER)
        implementation(Dependencies.OKHTTP)

        implementation(Dependencies.NAVIGATION_FRAGMENT)
        implementation(Dependencies.NAVIGATION_UI)

        implementation(Dependencies.LIFECYCLE_LIVEDATA)
        implementation(Dependencies.LIFECYCLE_VIEWMODEL)
        implementation(Dependencies.LIFECYCLE_COMMON_JAVA)
        kapt(Dependencies.LIFECYCLE_COMPILER)

        implementation(Dependencies.RXJAVA)
        implementation(Dependencies.RX_ANDROID)

        implementation(Dependencies.ROOM)
        kapt(Dependencies.ROOM_COMPILER)

        implementation(Dependencies.HILT)
        kapt(Dependencies.HILT_COMPILER)

        implementation(Dependencies.NAVER_MAP)

        implementation(Dependencies.CRYPTO)

        testImplementation(TestDependencies.JUNIT)
        androidTestImplementation(TestDependencies.EXT)
        androidTestImplementation(TestDependencies.ESPRESSO)
        implementation("com.google.android.gms:play-services-location:16.0.0")
``` 

## 🔍 Preview


## 💡 Core Function

1. CAFETI 검사 


<img src="https://user-images.githubusercontent.com/76424700/125820405-c1274452-373e-4630-93f2-fc16dd32891c.png" width="250" height="500"/> <img src="https://user-images.githubusercontent.com/76424700/125820330-064adb3c-efb9-406d-8f6c-8c581784787a.png" width="250" height="500"/>

``` C
       private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            val checkedButton = binding.radiogroupCafeColor.checkedRadioButtonId
            if (checkedButton != -1) {
                when (checkedButton) {
                    binding.radiobtnDark.id -> {
                        cafetiResultList.add(0)
                        getAPI()
                    }
                    binding.radiobtnLight.id -> {
                        cafetiResultList.add(1)
                        getAPI()
                    }
                    binding.radiobtnWarm.id -> {
                        cafetiResultList.add(2)
                        getAPI()
                    }
                    binding.radiobtnColorful.id -> {
                        cafetiResultList.add(3)
                        getAPI()
                    }
                }
            } else {
                CapinToastMessage.createCapinRejectToast(this@CafeColorActivity, "한가지 항목을 선택해주세요", 200)?.show()
            }
        }
    }

    private fun getAPI() {
        val requestCafetiData = RequestCafetiData(
            answers = cafetiResultList
        )
        val call: Call<ResponseCafetiData> =
            ServiceCreator.capinService.postCafeti(
                token,
                requestCafetiData
            )
        call.enqueue(object : Callback<ResponseCafetiData> {
            override fun onResponse(
                call: Call<ResponseCafetiData>,
                response: Response<ResponseCafetiData>
            ) {
                if (response.isSuccessful) {
                    Log.e("dfsd", "dfasdf")
                    Toast.makeText(this@CafeColorActivity, "CAFETI 검사 완료", Toast.LENGTH_SHORT)
                    val intent = Intent(this@CafeColorActivity, CafetiResultActivity::class.java)
                    intent.putExtra("cafeti_result", response.body()?.result)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@CafeColorActivity, "필요한 값이 없습니다.", Toast.LENGTH_SHORT)
                }
            }

            override fun onFailure(call: Call<ResponseCafetiData>, t: Throwable) {
                Log.d("CafeColorNetworkTest", "error:$t")
            }
        })

    }
``` 
2. 카핀맵 / 마이맵 

<img src="https://user-images.githubusercontent.com/76424700/125819992-1e8be074-79ca-4364-957a-527bb4a9dbd6.png" width="250" height="500"/> <img src="https://user-images.githubusercontent.com/76424700/125820219-4373dc5f-b2ec-494c-983a-08b5214e18a8.png" width="250" height="500"/>

``` C
카핀맵 / 마이맵 코드 
```

3. 마이페이지(핀)

<img src="https://user-images.githubusercontent.com/76424700/125820572-89111293-de65-4c30-93cf-81e3f0765612.png" width="250" height="500"/>

``` C
마이페이지(핀) 코드 
```

## 🙆‍♀️🙆‍♂️ Android developer & roles
손평화 :     
윤혁 :    
조성림 :     
홍은결 :   







