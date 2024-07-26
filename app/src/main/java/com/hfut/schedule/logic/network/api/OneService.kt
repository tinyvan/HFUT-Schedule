package com.hfut.schedule.logic.network.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface OneService {

    @GET ("api/operation/library/getBorrowNum")
    fun getBorrowBooks(@Header("Authorization") Authorization : String) : Call<ResponseBody>

    @GET ("api/operation/library/getSubscribeNum")
    fun getSubBooks(@Header("Authorization") Authorization : String) : Call<ResponseBody>
    @GET ("api/operation/thirdPartyApi/schoolcard/balance")
    fun getCard(@Header("Authorization") Authorization : String) : Call<ResponseBody>

//&redirect=https%253A%2F%2Fone.hfut.edu.cn%2Fhome%2Findex%253Fcode%253DOC-1414541-a9LSJGgabm-wNkFjbIcAvtlUBHJyNTfN&code=OC-1414541-a9LSJGgabm-wNkFjbIcAvtlUBHJyNTfN
    @GET ("api/auth/oauth/getToken?type=portal")
    fun getToken(@Query("redirect") redirect : String,
                  @Query("code") code : String
               ) : Call<ResponseBody>

    //空教室  JSON

    //api/operation/emptyClass/build?campus_code=03
    // {01}屯溪路校区  {02}翡翠湖  {03}宣城
    //获取JSON，各个楼建筑的building_code，再用building_code去查空教室
    @GET ("api/operation/emptyClass/build")
    fun selectBuilding(@Query("campus_code") campusID : String,
        @Header("Authorization") Authorization : String) : Call<ResponseBody>

    //api/operation/emptyClass/room?building_code=XC001&current=1

    @GET ("api/operation/emptyClass/room?current=1&size=30")
    fun searchEmptyRoom(@Query("building_code") building_code : String,
                        @Header("Authorization") Authorization : String) : Call<ResponseBody>

    @GET("api/leaver/third/finance/arrearsForPortal?type=1")
    fun getPay(@Query("xh") username : String) : Call<ResponseBody>
}