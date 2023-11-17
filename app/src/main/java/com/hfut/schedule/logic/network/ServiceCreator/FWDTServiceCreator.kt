package com.hfut.schedule.logic.network.ServiceCreator

import com.hfut.schedule.MyApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FWDTServiceCreator {
    val Client = OkHttpClient.Builder()
        //  .followRedirects(false)
        //.addInterceptor(RedirectInterceptor())
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl("http://172.31.248.26:8088/")
        .client(Client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    fun <T> create(service: Class<T>): T = retrofit.create(service)
    inline fun <reified  T> create() : T = create(T::class.java)
}