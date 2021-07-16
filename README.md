# 믿고 보는 카페 맵, C A : P I N ☕
<img src="https://user-images.githubusercontent.com/72273531/125905703-553419e1-ba79-4a6c-8c3a-2a4d9ca022ac.png" width="700" height="400"/>  

**오늘도 '카페 어디가지?' 고민하는 분들을 위한 카페맵 앱 서비스 / [Service OPR](https://www.notion.so/O-P-R-f521f789248347949bef26a1ef0d2354#2f8521e618114579b183cdcd99188d2f)**  
> **Team-CA:PIN Android**   
> SOPT 28th APPJAM    
> 프로젝트 기간: 2021.06.26 ~ 2021.07.16  

<br>


### 📌 Project Structure
------
☕ Capin_android  
📂 cafeti  
📂 category       
📂 customview  
📂 detail  
┣ 📂 menus          
📂 di      
📂 login   
📂 map  
┣ 📂 datasource    
┣ 📂 dto    
┣ 📂 entity    
┣ 📂 mapper   
┣ 📂 repository    
📂 mypage  
┣ 📂 api   
┃ ┣ 📂 request   
┃ ┣ 📂 response    
📂 mycategory    
📂 myreview    
📂 pin     
📂 network  
┣ 📂 response    
📂 preference  
📂 profile  
📂 review    
┣ 📂 write    
📂 signup  
📂 tagfilter  
📂 util  


<br>

    
### 🔗 Dependency
------
``` kotlin
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


<br>

### 🔍 Preview
------
> 1. 스플래시 / 회원가입
<img src="https://user-images.githubusercontent.com/72273531/125907329-d0ed2a28-bfaa-4b8a-be1b-b18222ec455c.jpg" width="700" height="466" />

> 2. 로그인 / 비밀번호 찾기
<img src="https://user-images.githubusercontent.com/72273531/125909203-3192b8d3-e73b-40bd-bd9b-b3afa7269241.jpg" width="700" height="393" />

> 3. CAFETI 검사
<img src="https://user-images.githubusercontent.com/72273531/125909376-b8c267f2-e0be-4a04-89ff-f410499d37d2.jpg" width="800" height="241" />

> 4. 카핀맵 / 마이맵
<img src="https://user-images.githubusercontent.com/72273531/125909423-69f48a6f-bbf3-4758-8203-290e8b8e04ba.jpg" width="700" height="466" />

> 5. 태그필터
<img src="https://user-images.githubusercontent.com/72273531/125911385-7f0fb04b-34c7-45ef-8305-0d46168d1410.jpg" width="700" height="393" />

> 6. 마이페이지
<img src="https://user-images.githubusercontent.com/72273531/125910989-f56b89b9-bd2c-45d6-97a8-01dd30132aa0.jpg" width="466" height="466" />

> 7. 카테고리 생성 / 수정 / 삭제
<img src="https://user-images.githubusercontent.com/72273531/125911530-715e92b3-a615-44b3-bd38-58d9f8052b13.jpg" width="700" height="264" />
<img src="https://user-images.githubusercontent.com/72273531/125911644-3fea53ce-25ef-417b-96bf-34892f906284.jpg" width="700" height="264" />

> 8. 카테고리 내부 핀 조회 / 삭제
<img src="https://user-images.githubusercontent.com/72273531/125911758-8b2ca8ae-24b4-4424-9fa0-e09768eec2ec.jpg" width="700" height="264" />



<br>

### 💡 Core Function
------

1. CAFETI 검사 


<img src="https://user-images.githubusercontent.com/76424700/125820405-c1274452-373e-4630-93f2-fc16dd32891c.png" width="250" height="500"/> <img src="https://user-images.githubusercontent.com/76424700/125820330-064adb3c-efb9-406d-8f6c-8c581784787a.png" width="250" height="500"/>

``` kotlin
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
                CapinToastMessage.createCapinRejectToast(this@CafeColorActivity, "한가지 항목을 선택해주세요", 135)?.show()
            }
        }
    }

    private fun getAPI() {
        val requestCafetiData = RequestCafetiData(
            answers = cafetiResultList
        )
        val call: Call<ResponseCafetiData> =
            ServiceCreator.capinApiService.postCafeti(
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
                    Toast.makeText(this@CafeColorActivity, "CAFETI 검사 완료", LENGTH_SHORT)
                    val intent = Intent(this@CafeColorActivity, CafetiResultActivity::class.java)
                    intent.putExtra("cafeti_result", response.body()?.result)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@CafeColorActivity, "필요한 값이 없습니다.", LENGTH_SHORT)
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

``` kotlin
카핀맵 / 마이맵 코드 
```

3. 마이페이지(핀)

<img src="https://user-images.githubusercontent.com/76424700/125820572-89111293-de65-4c30-93cf-81e3f0765612.png" width="250" height="500"/>

``` kotlin
마이페이지(핀) 코드 
```


<br>

### 🙆‍♀️🙆‍♂️ Android developer & roles
------
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/SONPYEONGHWA"><img src="https://avatars.githubusercontent.com/u/56873136?v=4" width="100px;" alt=""/><br /><sub><b>손평화</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=SONPYEONGHWA" title="Code">💻</a><br /><sub><b>카핀맵</b></sub></a><br /><sub><b>마이맵</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/malibinYun"><img src="https://avatars.githubusercontent.com/u/44341119?v=4" width="100px;" alt=""/><br /><sub><b>윤혁</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=malibinYun" title="Code">💻</a><br /><sub><b>카페 상세 보기</b></sub></a><br /><sub><b>카페 메뉴/리뷰</b></sub></a><br /></td>
     <td align="center"><a href="https://github.com/CHOSUNGRIM"><img src="https://avatars.githubusercontent.com/u/72273531?v=4" width="100px;" alt=""/><br /><sub><b>조성림</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=CHOSUNGRIM" title="Code">💻</a><br /><sub><b>마이페이지</b></sub></a><br /><sub><b>프로필 편집</b></sub></a><br /></td>
     <td align="center"><a href="https://github.com/hongeungual"><img src="https://avatars.githubusercontent.com/u/76424700?v=4" width="100px;" alt=""/><br /><sub><b>홍은결</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=hongeungual" title="Code">💻</a><br /><sub><b>로그인/회원가입</b></sub></a><br /><sub><b>CAFETI </b></sub></a><br /></td>
  </tr>
