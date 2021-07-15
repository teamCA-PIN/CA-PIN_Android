# 믿고 보는 카페 맵, C A : P I N ☕

## 📌 Project 

## 🔍 Preview

## 💡 Core Function
1. CAFETI 검사 
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
``` C
카핀맵 / 마이맵 코드 
```

3. 태그 필터
``` C
태그 필터 코드 
```
4. 마이페이지(핀)
``` C
마이페이지(핀) 코드 
```








