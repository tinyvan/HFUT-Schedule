package com.hfut.schedule.ui.Activity.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.hfut.schedule.App.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.ViewModel.UIViewModel
import com.hfut.schedule.logic.Enums.CardBarItems
import com.hfut.schedule.logic.utils.GetDate
import com.hfut.schedule.logic.utils.SharePrefs
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.logic.utils.StartApp
import com.hfut.schedule.ui.Activity.card.bills.main.BillItem
import com.hfut.schedule.ui.Activity.card.function.CardLimit
import com.hfut.schedule.ui.Activity.card.function.SearchBillsUI
import com.hfut.schedule.ui.Activity.card.function.SelecctDateRange
import com.hfut.schedule.ui.Activity.card.function.main.TodayBills
import com.hfut.schedule.ui.Activity.success.cube.Settings.Items.getUserInfo
import com.hfut.schedule.ui.Activity.success.focus.Focus.GetZjgdCard
import com.hfut.schedule.ui.Activity.success.main.saved.NetWorkUpdate
import com.hfut.schedule.ui.Activity.success.search.Search.More.Login
import com.hfut.schedule.ui.Activity.success.search.Search.News.NewsItem
import com.hfut.schedule.ui.UIUtils.DividerText
import com.hfut.schedule.ui.UIUtils.MyToast
import com.hfut.schedule.ui.UIUtils.WebViewScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(innerPadding : PaddingValues,vm : LoginSuccessViewModel,navController :  NavHostController,vmUI : UIViewModel) {
    //刷新
    var refreshing by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    // 用协程模拟一个耗时加载
    val scope = rememberCoroutineScope()
    var states = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            async {
                refreshing = true
                loading = true
                GetZjgdCard(vm,vmUI)
            }.await()
            async {
                delay(500)
                refreshing = false
                loading = false
                MyToast("刷新成功")
            }
        }
    })


    var showDialog by remember { mutableStateOf(false) }

    val sheetState_Range = rememberModalBottomSheetState()
    var showBottomSheet_Range by remember { mutableStateOf(false) }

    val sheetState_Search = rememberModalBottomSheetState()
    var showBottomSheet_Search by remember { mutableStateOf(false) }

    val sheetState_Settings = rememberModalBottomSheetState()
    var showBottomSheet_Settings by remember { mutableStateOf(false) }



    val sheetState_NFC = rememberModalBottomSheetState()
    var showBottomSheet_NFC by remember { mutableStateOf(false) }

    var showBottomSheet_Toady by remember { mutableStateOf(false) }
    val sheetState_Today = rememberModalBottomSheetState()




    val now = SharePrefs.prefs.getString("card_now","00")
    val settle = SharePrefs.prefs.getString("card_settle","00")

    var todaypay = 0.0
    var date = GetDate.Date_yyyy_MM_dd

    for (item in 0 until BillItem(vm).size) {
        val get = BillItem(vm)[item].effectdateStr
        val name = BillItem(vm)[item].resume
        val todaydate = get?.substringBefore(" ")
        var num = BillItem(vm)[item].tranamt.toString()

        //优化0.0X元Bug
        if(num.length == 1)
            num = "00$num"


        num = num.substring(0, num.length - 2) + "." + num.substring(num.length - 2)

        val num_float = num.toFloat()

        if (date == todaydate) {
            if (!name.contains("充值")) todaypay += num_float
        }

    }
    val num = todaypay.toString()
    val bd = BigDecimal(num)
    val str= bd.setScale(2, RoundingMode.HALF_UP).toString()

    val card = SharePrefs.prefs.getString("card","00")

    val test = vmUI.CardValue.value?.balance ?: card
    val name = vmUI.CardValue.value?.name ?: getUserInfo().name
    val nows = vmUI.CardValue.value?.now ?: now
    val settles = vmUI.CardValue.value?.settle ?: settle

    val auth = SharePrefs.prefs.getString("auth","")
    val url = MyApplication.ZJGDBillURL + "plat/pay" + "?synjones-auth=" + auth

    if (showDialog) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        actions = {
                            Row{
                                IconButton(onClick = { StartApp.StartUri(url) }) { Icon(painterResource(id = R.drawable.net), contentDescription = "") }
                                IconButton(onClick = { showDialog = false }) { Icon(painterResource(id = R.drawable.close), contentDescription = "") }
                            }
                        },
                        title = { Text("付款码") }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    WebViewScreen(url)
                }
            }
        }
    }


    if(showBottomSheet_NFC) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet_NFC = false
            },
            sheetState = sheetState_NFC
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("NFC复制说明") }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    ListItem(headlineContent = { Text(text = "实体卡芯片自带加密,复制后手机仅可在图书馆储物柜刷取，其余场景均不可用，包括但不限于宿舍门禁、寝室门禁、消费终端等") })
                }
            }
        }
    }

    if(showBottomSheet_Range) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet_Range = false
            },
            sheetState = sheetState_Range
        ) { SelecctDateRange(vm) }
    }

    if (showBottomSheet_Search) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet_Search = false
            },
            sheetState = sheetState_Search
        ) { SearchBillsUI(vm) }
    }

    if (showBottomSheet_Settings) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet_Settings = false
            },
            sheetState = sheetState_Settings
        ) { CardLimit(vm,vmUI) }
    }

    if(showBottomSheet_Toady) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet_Toady = false },
            sheetState = sheetState_Today
        ){
            TodayBills(vm)
        }
    }


    Box(modifier = Modifier
        .fillMaxHeight()
        .pullRefresh(states)) {
        LazyColumn {
            item {
                //////////////////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                Spacer(modifier = Modifier.height(5.dp))

                DividerText(text = "实体卡")
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    AnimatedVisibility(
                        visible = !refreshing,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column {
                            ListItem(
                                headlineContent = { Text(text = "$name 校园一卡通") },
                                trailingContent = {
                                    FilledTonalIconButton(onClick = {
                                        scope.launch {
                                            async {
                                                refreshing = true
                                                GetZjgdCard(vm,vmUI)
                                            }.await()
                                            async {
                                                delay(500)
                                                refreshing = false
                                                MyToast("刷新成功")
                                            }
                                        }
                                    }) {
                                        Icon(painter = painterResource(id = R.drawable.rotate_right), contentDescription = "")
                                    }
                                }
                            )
                            ListItem(headlineContent = { Text(text = "￥$test", fontSize = 28.sp) },
                                trailingContent = {
                                    Text(text = "待圈存 ￥${settles}\n卡余额 ￥${nows}")
                                })
                            Row {
                                ListItem(
                                    modifier = Modifier
                                        .weight(.5f)
                                        .clickable {
                                            if (test != null) {
                                                //余额不足//未登录//正常
                                                if (test != "00" && test!!.toDouble() < 10) {
                                                    StartApp.openAlipay(MyApplication.AlipayCardURL)
                                                } else if (test == "00") {
                                                    Login()
                                                } else {
                                                    MyToast("未检测出问题,若实体卡仍异常请咨询有关人士")
                                                }
                                            }
                                        },
                                    headlineContent = {
                                        if (test != null)
                                            Text(text = if(test != "00" && test!!.toDouble() < 10) "余额不足"  else if(test == "00") "未登录" else "正常")
                                    },
                                    overlineContent = { Text(text = "状态")},
                                    leadingContent = {
                                        if (test != null) {
                                            Icon(painter =
                                            painterResource(id =
                                            if(test != "00" && test!!.toDouble() < 10) R.drawable.add_circle  else if(test == "00") R.drawable.login else R.drawable.check_circle
                                            )  , contentDescription = "")
                                        }
                                    })
                                ListItem(
                                    modifier = Modifier
                                        .weight(.5f)
                                        .clickable { showBottomSheet_Toady = true },
                                    headlineContent = { Text(text = "￥$str",fontWeight = FontWeight.Bold) },
                                    overlineContent = { Text(text = "今日支出")},
                                    leadingContent = { Icon(painter = painterResource(id = R.drawable.send_money), contentDescription = "")})
                            }
                            limitRow(vmUI)
                        }
                    }
                }
                DividerText(text = "功能")
                ListItem(
                    headlineContent = { Text(text = "账单") },
                    supportingContent = { Text(text = "按消费先后查看交易记录")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.receipt_long), contentDescription = "") },
                    modifier = Modifier.clickable { turnToBottomBar(navController,CardBarItems.BILLS.name) }
                )
                ListItem(
                    headlineContent = { Text(text = "统计") },
                    supportingContent = { Text(text = "按时间段归纳统计消费")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.leaderboard), contentDescription = "") },
                    modifier = Modifier.clickable { turnToBottomBar(navController,CardBarItems.COUNT.name) }
                )
                ListItem(
                    headlineContent = { Text(text = "充值") },
                    supportingContent = { Text(text = "跳转至支付宝校园卡页面")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.add_card), contentDescription = "")},
                    modifier = Modifier.clickable { StartApp.openAlipay(MyApplication.AlipayCardURL) }
                )
                ListItem(
                    headlineContent = { Text(text = "搜索") },
                    supportingContent = { Text(text = "仅检索标题")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")},
                    modifier = Modifier.clickable { showBottomSheet_Search = true }
                )
                ListItem(
                    headlineContent = { Text(text = "限额") },
                    supportingContent = { Text(text = "超出设置的日额度后需在支付机输入密码")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.block), contentDescription = "")},
                    modifier = Modifier.clickable { showBottomSheet_Settings = true }
                )
                ListItem(
                    headlineContent = { Text(text = "卡状态") },
                    supportingContent = { Text(text = "挂失 解挂 冻结")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.pie_chart), contentDescription = "")},
                    modifier = Modifier.clickable { MyToast("暂未开发") }
                )
                ListItem(
                    headlineContent = { Text(text = "付款码") },
                    supportingContent = { Text(text = "在支持扫码的食堂支付机使用以替代实体卡")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.barcode), contentDescription = "")},
                    modifier = Modifier.clickable { showDialog = true }
                )
                ListItem(
                    headlineContent = { Text(text = "范围支出") },
                    supportingContent = { Text(text = "手动点选范围查询总消费")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.settings_ethernet), contentDescription = "")},
                    modifier = Modifier.clickable { showBottomSheet_Range = true }
                )
                ListItem(
                    headlineContent = { Text(text = "网电缴费") },
                    supportingContent = { Text(text = "查询网电使用情况并缴费")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.corporate_fare), contentDescription = "")},
                    modifier = Modifier.clickable { MyToast("暂未开发") }
                )
                ListItem(
                    headlineContent = { Text(text = "实体卡复制") },
                    supportingContent = { Text(text = "对实体卡使用设备NFC复制功能")},
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.contactless), contentDescription = "")},
                    modifier = Modifier.clickable { showBottomSheet_NFC = true }
                )
                Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
            }
        }
        PullRefreshIndicator(refreshing, states,
            Modifier
                .align(Alignment.TopCenter)
                .padding(innerPadding))
    }
}

fun turnToBottomBar(navController :  NavHostController,route : String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun limitRow(vmUI : UIViewModel) {
    val limit by remember { mutableStateOf(vmUI.CardValue.value?.autotrans_limite ?: prefs.getString("card_limit","0")) }
    val amt by remember { mutableStateOf(vmUI.CardValue.value?.autotrans_amt?: prefs.getString("card_amt","0")) }


    Row {
        ListItem(
            modifier = Modifier
                .weight(.5f)
                .clickable { },
            headlineContent = { Text(text = "￥${limit}") },
            overlineContent = { Text(text = "自动转账限额")},
            leadingContent = { Icon(painter = painterResource(id = R.drawable.block), contentDescription = "")})
        ListItem(
            modifier = Modifier
                .weight(.5f)
                .clickable { },
            headlineContent = { Text(text = "￥${amt}") },
            overlineContent = { Text(text = "自动转账金额")},
            leadingContent = { Icon(painter = painterResource(id = R.drawable.do_not_disturb_on), contentDescription = "")})
    }
}