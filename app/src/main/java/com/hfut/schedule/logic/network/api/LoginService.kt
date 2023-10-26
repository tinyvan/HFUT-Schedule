package com.hfut.schedule.logic.network.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
//获取AES加密的Key
    @GET("cas/checkInitParams")
    fun getKey() : Call<ResponseBody>
    //获取登录所需要的Cookie的1/3
    @GET ("cas/login?service=http%3A%2F%2Fjxglstu.hfut.edu.cn%2Feams5-student%2Fneusoft-sso%2Flogin")
    fun getCookie() : Call<ResponseBody>

    //@GET("")
//教务系统登录?service=http%3A%2F%2Fjxglstu.hfut.edu.cn%2Feams5-student%2Fneusoft-sso%2Flogin
    @FormUrlEncoded
    @POST("cas/login?service=http%3A%2F%2Fjxglstu.hfut.edu.cn%2Feams5-student%2Fneusoft-sso%2Flogin")
    fun login(@Field("username") username : String,
              @Field("password") password : String,
              @Field("execution") execution : String,
              @Field("_eventId") eventId : String,
              //@Field("capcha") capcha : String,
              //@Field("geolocation") geolocation : String
              ) : Call<ResponseBody>
}