package com.example.retrofitsample
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/*
비동기 방식을 사용해야 하기에 Call을 사용한다
Call<서버에서 넘겨줄 데이터 형식 정의 >
* */
interface UpbitAPI {
    @GET("v1/market/all")
    fun initListCoin(
    ): Call<List<Coin>>


    @GET("/mobilemembers/AllList")
    fun getMemberAll(): Call<List<Member>>

    @POST("/mobilemembers/new")
    fun registerMember(@Body member : Member): Call<ResponseBody>

    @POST("/mobilemembers/login")
    fun loginMember(@Body loginRequest : Member): Call<ResponseBody>
}