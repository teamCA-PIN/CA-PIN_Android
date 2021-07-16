# 믿고 보는 카페 맵, C A : P I N ☕

<img src="https://user-images.githubusercontent.com/72273531/125905703-553419e1-ba79-4a6c-8c3a-2a4d9ca022ac.png" />  

### 📌 Project Description
------

**오늘도 '카페 어디가지?' 고민하는 분들을 위한 카페맵 앱 서비스 / [Service OPR](https://www.notion.so/O-P-R-f521f789248347949bef26a1ef0d2354#2f8521e618114579b183cdcd99188d2f)**  
> **Team-CA:PIN Android**   
> SOPT 28th APPJAM    
> 프로젝트 기간: 2021.06.26 ~ 2021.07.16  

<br>





<br>


###  💡 Main Feature & 🔍Preview  
------
> 1. 스플래시 / 회원가입 / 로그인 / 비밀번호 찾기
<img src="https://user-images.githubusercontent.com/72273531/125907329-d0ed2a28-bfaa-4b8a-be1b-b18222ec455c.jpg" width="700" height="466" />
<img src="https://user-images.githubusercontent.com/72273531/125909203-3192b8d3-e73b-40bd-bd9b-b3afa7269241.jpg" width="700" height="390" />

> 2. 카페 이용 유형 검사, CAFETI
<img src="https://user-images.githubusercontent.com/72273531/125909376-b8c267f2-e0be-4a04-89ff-f410499d37d2.jpg" width="800" height="241" />

> 3. 믿고 보는 카핀 맵 / 내가 핀한 카페들만 모아보는 마이맵
<img src="https://user-images.githubusercontent.com/72273531/125909423-69f48a6f-bbf3-4758-8203-290e8b8e04ba.jpg" width="700" height="466" />

> 4. 원하는 특성의 카페만 모아볼 수 있는 태그 검색
<img src="https://user-images.githubusercontent.com/72273531/125911385-7f0fb04b-34c7-45ef-8305-0d46168d1410.jpg" width="700" height="390" />

> 5. 맛, 분위기 취향이 비슷한 유저들의 카페 리뷰
<img src="https://user-images.githubusercontent.com/72273531/125943215-84bfd2ca-c6cf-4472-8d1d-0c34e8e62692.png" width="700" height="390" />

> 6. 나의 핀과 리뷰를 관리하는 마이페이지
<img src="https://user-images.githubusercontent.com/72273531/125910989-f56b89b9-bd2c-45d6-97a8-01dd30132aa0.jpg" width="466" height="466" />

> 7. 카테고리 생성 / 수정 / 삭제
<img src="https://user-images.githubusercontent.com/72273531/125911530-715e92b3-a615-44b3-bd38-58d9f8052b13.jpg" width="700" height="264" />
<img src="https://user-images.githubusercontent.com/72273531/125911644-3fea53ce-25ef-417b-96bf-34892f906284.jpg" width="700" height="264" />

> 8. 카테고리 내부 핀 조회 / 삭제
<img src="https://user-images.githubusercontent.com/72273531/125911758-8b2ca8ae-24b4-4424-9fa0-e09768eec2ec.jpg" width="700" height="264" />


<br>

<br>

### ✅ 개발 조건  
---
1. Cooperation  
[Coding Convention](https://www.notion.so/Coding-Convention-34d4528be138482ca3e40576eade5864)  
[Commit, Branch Management](https://www.notion.so/Commit-branch-101d28c214284b3994c337ee5d196f52)  
[Package Convention](https://www.notion.so/Package-Convention-d938c8bc004540929cd093036c6c1eaf)  
[Tech Stack](https://www.notion.so/de1c3900bda247ffb9bfb3e3f0f29f33)  
[Kanban Board](https://www.notion.so/6a425a72f84849198c49aac30f1270bd?v=12bc935670474d8b827b6faef9e0ee03)  

2. Use Kotlin ✔   

3. Consider View Size  
```Kotlin
android:layout_width="0dp"
android:layout_height="0dp"
app:layout_constraintWidth_percent="0.243"
app:layout_constraintDimensionRatio="1:1.031"
app:layout_constraintVertical_bias="0.105"
```

4. Lifecycle of Fragment  
```Kotlin
class AutoClearedValue<T: Any>: ReadWriteProperty<Fragment, T>, LifecycleObserver {
    private var _value:T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        _value ?: throw  IllegalStateException("AutoClearedValue is not available")


    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        _value = value
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        _value = null
    }
}
```
 
5. Use ShapeDrawable & StateListDrawable ✔  
```Kotlin
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/pointcolor_1"/>
    <corners android:radius="11dp"/>
</shape>

<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true" android:drawable="@drawable/colorchip_selected_1"/>
    <item android:state_checked="false" android:drawable="@drawable/colorchip_basic_1"/>
</selector>
```

6. color.xml  
```Kotlin
<resources>
    ...
    <color name="gray_1">#f9f9f9</color>
    <color name="pointcolor_1">#a98e7a</color>
    <color name="subcolor_blue_3">#bbdcef</color>
    <color name="subcolor_blue_2">#cee4f1</color>
    <color name="subcolor_blue_1">#dcecf5</color>
    <color name="subcolor_blue_4">#91c2de</color>
    <color name="gray_3">#c4c4c4</color>
    <color name="gray_2">#ededed</color>
    <color name="gray_4">#878787</color>
    <color name="subcolor_brown_3">#947d6c</color>
    ...
</resources>
```


<br>

<br>



### 👨‍👨‍👧‍👧Android developer & roles
------
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/SONPYEONGHWA"><img src="https://user-images.githubusercontent.com/72273531/125926480-93c5341c-3e7e-4d1a-83cf-24424c9f18a9.png";" alt=""/><br /><sub><b>손평화</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=SONPYEONGHWA" title="Code">💻</a><br /><sub><b>카핀맵</b></sub></a><br /><sub><b>마이맵</b></sub></a><br /><sub><b>리뷰 작성</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/malibinYun"><img src="https://user-images.githubusercontent.com/72273531/125926366-bb800691-a15e-44c1-b4bd-cd27d55e521b.png";" alt=""/><br /><sub><b>윤혁</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=malibinYun" title="Code">💻</a><br /><sub><b>카페 상세 보기</b></sub></a><br /><sub><b>카페 메뉴/리뷰</b></sub></a><br /></td>
     <td align="center"><a href="https://github.com/CHOSUNGRIM"><img src="https://user-images.githubusercontent.com/72273531/125926250-90af849a-3517-474a-b8a3-9039c99a1249.png";" alt=""/><br /><sub><b>조성림</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=CHOSUNGRIM" title="Code">💻</a><br /><sub><b>마이페이지</b></sub></a><br /><sub><b>마이 카테고리/리뷰</b></sub></a><br /></td>
     <td align="center"><a href="https://github.com/hongeungual"><img src="https://user-images.githubusercontent.com/72273531/125926104-23cdc2ab-6a8f-486b-a5b9-33aeebd03c90.png";" alt=""/><br /><sub><b>홍은결</b></sub></a><br /><a href="https://github.com/teamCA-PIN/CA-PIN_Android/commits?author=hongeungual" title="Code">💻</a><br /><sub><b>로그인/회원가입</b></sub></a><br /><sub><b>CAFETI 검사</b></sub></a><br /></td>
  </tr>
</table>

