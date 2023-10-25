package com.hfut.schedule.ui.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.schedule.logic.network.LoginService
import com.hfut.schedule.logic.network.ServiceCreator
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var livedata = MutableLiveData<String>()
    val api = ServiceCreator.create(LoginService::class.java)

    fun login(username : String,password : String) {// 创建一个Call对象，用于发送异步请求
        val call = api.login(username, password,"e1s1","submit")
        Log.d("账号",username)
        Log.d("密码",password)
        // 在子线程中执行请求，并在回调中处理响应结果
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // 请求成功，获取响应体
                val body = response.body()
                // 将响应体转换为字符串，并赋值给livedata
                livedata.value = body?.string()
                if(response.isSuccessful()) {
                    val Result = response.body()?.toString()
                    Log.d("测试","成功，${response.code()}")
                    Log.d("响应头", response.headers().toString())
                    Log.d("响应信息",response.message())
                    Log.d("响应主体",Result!!)
                }
                 else Log.d("测试","失败，${response.code()},${response.message()}")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                 Log.d("测试","失")
                t.printStackTrace()
            }
            })
    }

    fun getKey() {

        val call = api.getKey()
        // 在子线程中执行请求，并在回调中处理响应结果
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // 请求成功，获取响应体
                val body = response.body()
                // 将响应体转换为字符串，并赋值给livedata
                livedata.value = body?.string()
                if(response.isSuccessful()) Log.d("测试","成功，${response.headers()} ${response.code()}")
                else Log.d("测试","失败，${response.code()},${response.message()}")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("测试","失")
                t.printStackTrace()
            }
        })

        //写函数，传递Key到Mainactivity
    }
    }



