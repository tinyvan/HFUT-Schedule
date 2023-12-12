package com.hfut.schedule.logic.network.interceptor

import android.preference.PreferenceManager
import android.util.Log
import com.hfut.schedule.MyApplication
import com.hfut.schedule.logic.SharePrefs
import okhttp3.Interceptor
import okhttp3.Response

class GotoInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
           // response.
            val code = response.headers("Location").toString()
            if (code.contains("code=")) SharePrefs.Save("code",code)

          //  Log.d("响应tou",response.headers.toString())
            if (response.headers.toString().contains("synjones")) {
            var key =   response.headers("Location").toString()
                key = key.substringAfter("synjones-auth=")
                key = key.substringBefore("]")
                SharePrefs.Save("auth",key)
            }

            //response.body?.let { Log.d("响应", it.string()) }
            return response
        }



}