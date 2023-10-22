package com.hfut.schedule.logic.network.OkHttp

import android.preference.PreferenceManager
import android.util.Log
import com.hfut.schedule.MyApplication.Companion.context
import okhttp3.Interceptor
import okhttp3.Response


// 创建一个接收拦截器，实现Interceptor接口，重写intercept方法
class ReceiveCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 获取响应对象
        val response = chain.proceed(chain.request())
        // 获取响应头中的Set-Cookie字段
        val cookies = response.headers("Cookie")
        // 定义一个变量用于存储LOGIN_FLAVORING
        var cookieValue = ""
        // 遍历cookies列表，找到LOGIN_FLAVORING
        for (cookie in cookies) {
            if (cookie.contains("LOGIN_FLAVORING")) {
                // 提取出LOGIN_FLAVORING的值
                cookieValue = cookie.substringBefore(";")
                break
            }
        }
        Log.d("测试2",cookieValue)
        // 如果cookieValue不为空，就将其保存到SharedPreferences中

        if (cookieValue.isNotEmpty()) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString("cookie", cookieValue).apply()
        }
        // 返回响应对象
        return response
    }
}