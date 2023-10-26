package com.hfut.schedule.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.hfut.schedule.logic.AESEncrypt
import com.hfut.schedule.R
import com.hfut.schedule.ui.ViewModel.LoginViewModel

class LoginActivity : ComponentActivity() {
    private val vm by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //if (Build.VERSION.SDK_INT > 9) {
           // val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
          //  StrictMode.setThreadPolicy(policy)
      //  }

        val accountET: EditText = findViewById(R.id.AccountET)
        val passwordET: EditText = findViewById(R.id.PasswordET)
        val loginButton: Button = findViewById(R.id.LoginButton)



        vm.getCookie()

        //得到AESKey
        vm.getKey()
        Thread.sleep(3000)
       val prefs = getSharedPreferences("com.hfut.schedule_preferences", Context.MODE_PRIVATE)
        val key = prefs.getString("cookie", "")



        key?.let { Log.d("传送", it) }

            loginButton.setOnClickListener {
                val inputAES = passwordET.editableText.toString()
                val username = accountET.editableText.toString()
                val outputAES = key?.let { it1 -> AESEncrypt.encrypt(inputAES, it1) }

                outputAES?.let { it1 -> vm.login(username, it1) }

              //  val it =Intent(this,UIAcitivity::class.java)
              //  startActivity(it)
            }
        }
    }


