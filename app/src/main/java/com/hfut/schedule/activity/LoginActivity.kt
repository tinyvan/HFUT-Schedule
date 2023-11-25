package com.hfut.schedule.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hfut.schedule.MyApplication
import com.hfut.schedule.logic.AESEncrypt
import com.hfut.schedule.R
import com.hfut.schedule.logic.SharePrefs.prefs_key
import com.hfut.schedule.ui.ComposeUI.AboutAlertDialog
import com.hfut.schedule.ui.ComposeUI.TransparentSystemBars
import com.hfut.schedule.ui.ComposeUI.checkDate
import com.hfut.schedule.ViewModel.LoginViewModel
import com.hfut.schedule.ui.DynamicColor.DynamicColorViewModel
import com.hfut.schedule.ui.theme.DynamicColr
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val vm by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private val dynamicColorViewModel: DynamicColorViewModel by viewModels()
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            var dynamicColorEnabled by remember { mutableStateOf(true) }
            val currentTheme by dynamicColorViewModel.currentTheme
            DynamicColr( context = applicationContext,
                currentTheme = currentTheme,
                dynamicColor = dynamicColorEnabled){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransparentSystemBars()
                   LoginUI(vm)
                   // test()
                }
            }
        }

        checkDate("2023-09-01","2024-02-01") // 定义一个函数，超出日期不允许使用


        lifecycleScope.apply {
            launch { vm.getCookie() }
            launch {  vm.getKey() }
            launch {  vm.My() }
        }//协程并行执行，提高效率

        }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginUI(vm : LoginViewModel) {
        val openAlertDialog = remember { mutableStateOf(false) }
        if (openAlertDialog.value) {
            AboutAlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = { openAlertDialog.value = false },
                dialogTitle = "使用注意",
                dialogText = "连接Host失败是偶然响应问题,更换网络重进或再点登录\n\n本地课表需登陆过一次后可使用,自动保存\n\n尽量不要重复点击登录,如果仍无法登录,可联系我",
                icon = Icons.Default.Warning
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text("肥工教务通") },

                    actions = {
                        IconButton(onClick = {openAlertDialog.value = true}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "主页")
                        }
                    }
                )
            }
        ) {innerPadding ->
            //列表
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TwoTextField(vm)
            }
        }
    }



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TwoTextField(vm : LoginViewModel) {

        var hidden by rememberSaveable { mutableStateOf(true) }

        val prefs = MyApplication.context.getSharedPreferences("com.hfut.schedule_preferences", Context.MODE_PRIVATE)
        val Savedusername = prefs.getString("Username", "")
        val Savedpassword = prefs.getString("Password","")

        var username by remember { mutableStateOf(Savedusername ?: "") }
        var inputAES by remember { mutableStateOf(Savedpassword ?: "") }

        // 创建一个动画值，根据按钮的按下状态来改变阴影的大小

        Column(modifier = Modifier.fillMaxWidth()) {
            val interactionSource = remember { MutableInteractionSource() }
            val interactionSource2 = remember { MutableInteractionSource() } // 创建一个
            val isPressed by interactionSource.collectIsPressedAsState()
            val isPressed2 by interactionSource2.collectIsPressedAsState()

            val scale = animateFloatAsState(
                targetValue = if (isPressed) 0.9f else 1f, // 按下时为0.9，松开时为1
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "" // 使用弹簧动画
            )

            val scale2 = animateFloatAsState(
                targetValue = if (isPressed2) 0.9f else 1f, // 按下时为0.9，松开时为1
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "" // 使用弹簧动画
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = username,
                    onValueChange = {username = it },
                    label = { Text("学号" ) },
                    singleLine = true,
                    // placeholder = { Text("请输入正确格式")},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent, // 有焦点时的颜色，透明
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent, // 无焦点时的颜色，绿色
                    ),
                    leadingIcon = { Icon( painterResource(R.drawable.person), contentDescription = "Localized description") },

                    trailingIcon = {
                        IconButton(onClick = {
                            username = ""
                            inputAES = ""
                        }) {
                            Icon(painter = painterResource(R.drawable.close), contentDescription = "description")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = inputAES,
                    onValueChange = { inputAES = it },
                    label = { Text("密码") },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent, // 有焦点时的颜色，透明
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent, // 无焦点时的颜色，绿色
                    ),
                    supportingText = { Text("密码为信息门户,请勿频繁点击登录!")},
                    visualTransformation = if (hidden) PasswordVisualTransformation()
                    else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(painterResource(R.drawable.key), contentDescription = "Localized description") },
                    trailingIcon = {
                        IconButton(onClick = { hidden = !hidden }) {
                            val icon =
                                if (hidden) painterResource(R.drawable.visibility_off)
                                else painterResource(R.drawable.visibility)
                            val description =
                                if (hidden) "展示密码"
                                else "隐藏密码"
                            Icon(painter = icon, contentDescription = description)


                        }
                    },
                    shape = MaterialTheme.shapes.medium
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){



                Button(
                    onClick = {


                       // val prefs = MyApplication.context.getSharedPreferences("com.hfut.schedule_preferences", Context.MODE_PRIVATE)
                        //val key = prefs.getString("cookie", "")

                        val outputAES = prefs_key?.let { it1 -> AESEncrypt.encrypt(inputAES, it1) }



                        val sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.context)
                        if(sp.getString("Username","") != username){ sp.edit().putString("Username", username).apply() }
                        if(sp.getString("Password","") != inputAES){ sp.edit().putString("Password", inputAES).apply() }
                        val ONE = "LOGIN_FLAVORING=" + prefs_key
                        outputAES?.let { it1 -> vm.login(username, it1,ONE) }





                        CoroutineScope(Job()).launch {

                            delay(1250)


                           // vm.code.value?.let { Log.d("代码", it) }

                            if (vm.code.value.toString() == "XXX" || vm.code.value == null) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(MyApplication.context, "连接Host失败,请再次尝试登录", Toast.LENGTH_SHORT).show()
                                    vm.getCookie()
                                }

                            }
                            if (vm.code.value.toString() == "401")
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(MyApplication.context, "账号或密码错误", Toast.LENGTH_SHORT).show()
                                    vm.getCookie()
                                }


                            if (vm.code.value.toString() == "200" || username.length != 10)
                                withContext(Dispatchers.Main) { Toast.makeText(MyApplication.context, "请输入正确的账号", Toast.LENGTH_SHORT) .show()

                                }

                            if (vm.code.value.toString() == "302") {

                                if (vm.location.value.toString() == MyApplication.RedirectURL) {
                                    Toast.makeText(MyApplication.context, "重定向失败,请重新登录", Toast.LENGTH_SHORT).show()
                                    vm.getCookie()
                                }

                                if (vm.location.value.toString().contains("ticket")) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(MyApplication.context, "登陆成功", Toast.LENGTH_SHORT).show()

                                        val it = Intent(MyApplication.context, LoginSuccessAcitivity::class.java).apply {
                                            addFlags(FLAG_ACTIVITY_NEW_TASK)
                                            putExtra("Grade", username.substring(2, 4))
                                        }
                                        MyApplication.context.startActivity(it)
                                    }
                                }

                                else {
                                    withContext(Dispatchers.Main) {
                                    Toast.makeText(MyApplication.context, "重定向失败,请重新登录", Toast.LENGTH_SHORT).show()
                                        vm.getCookie()}
                                }
                            }
                        }
                    }, modifier = Modifier.scale(scale.value),
                    interactionSource = interactionSource

                ) {
                    Text("登录")
                }
                Spacer(modifier = Modifier.width(15.dp))

                FilledTonalButton(
                    onClick = {
                        val it = Intent(MyApplication.context,SavedCoursesActivity::class.java)
                        it.addFlags(FLAG_ACTIVITY_NEW_TASK)
                        MyApplication.context.startActivity(it)
                    },modifier = Modifier.scale(scale2.value),
                    interactionSource = interactionSource2,

                    ) {

                    Text("本地课表")

                }
            }

        }



    }


}

