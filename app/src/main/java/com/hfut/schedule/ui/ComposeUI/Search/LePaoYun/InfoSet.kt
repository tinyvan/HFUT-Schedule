package com.hfut.schedule.ui.ComposeUI.Search.LePaoYun

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.logic.SharePrefs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoSet() {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val savedToken = SharePrefs.prefs.getString("Yuntoken","")
    var inputToken by remember { mutableStateOf(savedToken ?: "") }


    val savedRequestBody = SharePrefs.prefs.getString("YunRequestBody","")
    var inputRequestBody by remember { mutableStateOf(savedRequestBody ?: "") }


    val scale = animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f, // 按下时为0.9，松开时为1
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "" // 使用弹簧动画
    )

    Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("信息配置") },
                        actions = {
                            FilledTonalIconButton(
                                modifier = Modifier.scale(scale.value).padding(25.dp),
                                interactionSource = interactionSource,
                                onClick = {
                                   // showBottomSheet_inputchanged
                                    SharePrefs.Save("Yuntoken",inputToken)
                                    SharePrefs.Save("YunRequestBody",inputRequestBody)
                                    Toast.makeText(MyApplication.context,"已保存",Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(Icons.Filled.Check, contentDescription = "")
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize()
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 5.dp),
                        shape = MaterialTheme.shapes.medium
                    ){
                        ListItem(
                            headlineContent = { Text(text = "请通过抓包获取常用设备的token,多设备登录会带来风险,所以要手动填写")},
                            leadingContent = { Icon(painterResource(id = R.drawable.info), contentDescription = "")}
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        TextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 15.dp),
                            value = inputToken,
                            onValueChange = { inputToken = it },
                            label = { Text("token") },
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,),
                            leadingIcon = { Icon( painterResource(R.drawable.key), contentDescription = "Localized description") }
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        TextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 15.dp),
                            value = inputRequestBody,
                            onValueChange = { inputRequestBody = it },
                            label = { Text("RequestBody,非必须,但填写后可查看建跑记录") },
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,),
                            leadingIcon = { Icon( painterResource(R.drawable.net), contentDescription = "Localized description") }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
}
