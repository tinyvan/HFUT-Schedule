package com.hfut.schedule.ui.ComposeUI.Saved

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hfut.schedule.App.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.ViewModel.LoginViewModel
import com.hfut.schedule.logic.datamodel.NavigationBarItemData
import com.hfut.schedule.logic.utils.GetDate
import com.hfut.schedule.logic.utils.GetDate.Benweeks
import com.hfut.schedule.logic.utils.GetDate.Date_MM_dd
import com.hfut.schedule.logic.utils.SharePrefs
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.ui.ComposeUI.BottomBar.SearchScreen
import com.hfut.schedule.ui.ComposeUI.BottomBar.SettingsScreen
import com.hfut.schedule.ui.ComposeUI.BottomBar.TodayScreen
import com.hfut.schedule.ui.ComposeUI.BottomBar.getName
import com.hfut.schedule.ui.ComposeUI.SavedCourse.SaveCourse
import com.hfut.schedule.ui.ComposeUI.Search.NotificationsCenter.NotificationItems
import com.hfut.schedule.ui.ComposeUI.Search.NotificationsCenter.getNotifications
import com.hfut.schedule.ui.ComposeUI.Settings.getMyVersion
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoNetWork(vm : LoginSuccessViewModel,vm2 : LoginViewModel) {
   // val prefs = MyApplication.context.getSharedPreferences("com.hfut.schedule_preferences", Context.MODE_PRIVATE)
    val navController = rememberNavController()
    var isEnabled by remember { mutableStateOf(true) }
    val switch = prefs.getBoolean("SWITCH",true)
    var showlable by remember { mutableStateOf(switch) }
    var first = "1"
    val hazeState = remember { HazeState() }
    CoroutineScope(Job()).launch { NetWorkUpdate(vm, vm2) }
    var topBarSwap by remember { mutableStateOf(1) }
    var showBadge by remember { mutableStateOf(false) }
    if (MyApplication.version != getMyVersion()) showBadge = true
    val switchblur = prefs.getBoolean("SWITCHBLUR",true)
    var blur by remember { mutableStateOf(switchblur) }
   // val savenum = prefs.getInt("GradeNum",0) + prefs.getInt("ExamNum",0) + prefs.getInt("Notifications",0)
    //val getnum = getGrade().size + getExam().size + getNotifications().size
    //if (savenum != getnum) showBadge2 = true


//判定是否以聚焦作为第一页
    first = when (prefs.getBoolean("SWITCHFOCUS",true)) {
        true -> "2"
        false -> "1"
    }
    var showAll by remember { mutableStateOf(false) }


    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) {
        SharePrefs.Save("Notifications", getNotifications().size.toString())
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = .45f),
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("消息中心") }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ){
                    NotificationItems()
                }
            }
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.hazeChild(state = hazeState, blurRadius = 35.dp, tint = Color.Transparent, noiseFactor = 0f),
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = if(blur).50f else 1f),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(texts(vm,topBarSwap)) },
                actions = {
                    if(topBarSwap == 0) {
                        androidx.compose.material.IconButton(onClick = { showAll = !showAll
                        }) { Icon(painter = painterResource(id = if (showAll) R.drawable.collapse_content else R.drawable.expand_content), contentDescription = "") }
                    }
                    if(topBarSwap == 1) {
                        TextButton(onClick = { showBottomSheet = true }) {
                            BadgedBox(badge = {
                                if (getNotifications().size.toString() != prefs.getString("Notifications",""))
                                    Badge()
                            }) { Icon(painterResource(id = R.drawable.notifications), contentDescription = "") }
                        }
                    }
                },
                )
        },
        bottomBar = {
            NavigationBar(containerColor = if(blur) MaterialTheme.colorScheme.primaryContainer.copy(.25f) else ListItemDefaults.containerColor ,
                modifier = Modifier
                    .hazeChild(state = hazeState, blurRadius = 35.dp, tint = Color.Transparent, noiseFactor = 0f)) {
            //    val image = AnimatedImageVector.animatedVectorResource(R.drawable.ic_hourglass_animated)
              //  var atEnd by remember { mutableStateOf(false) }

                val items = listOf(
                    NavigationBarItemData("1", "课程表", painterResource(R.drawable.calendar ), painterResource(R.drawable.calendar_month_filled)),
                    NavigationBarItemData("2","聚焦", painterResource(R.drawable.lightbulb), painterResource(R.drawable.lightbulb_filled)),
                    NavigationBarItemData("search","查询中心", painterResource(R.drawable.search),painterResource(R.drawable.search_filledx)),
                    NavigationBarItemData("3","选项", painterResource(R.drawable.cube), painterResource(R.drawable.deployed_code_filled))
                )
                items.forEach { item ->
                    val route = item.route
                    val selected = navController.currentBackStackEntryAsState().value?.destination?.route == route
                    NavigationBarItem(
                        selected = selected,
                        alwaysShowLabel = showlable,
                        enabled = isEnabled,
                        onClick = {
                            if(item == items[0]) topBarSwap = 0
                            if(item == items[1]) topBarSwap = 1
                            if(item == items[2]) topBarSwap = 2
                            if(item == items[3]) topBarSwap = 3
                           //     atEnd = !atEnd
                            if (!selected) {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        label = { Text(text = item.label) },
                        icon = {
                            BadgedBox(badge = {
                                if (item == items[3]){
                                    if (showBadge)
                                    Badge{ Text(text = "1")}
                                }
                            }) { Icon(if(selected)item.filledIcon else item.icon, contentDescription = item.label) }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = first, modifier = Modifier
          //  .padding(innerPadding)
            //.fillMaxSize()
            .haze(
                state = hazeState,
                backgroundColor = MaterialTheme.colorScheme.surface,
            )) {
            composable("1") { SaveCourse(showAll, innerPadding) }
            composable("2") { TodayScreen(vm,vm2,innerPadding, blur) }
            composable("search") { SearchScreen(vm,true,innerPadding) }
            composable("3") { SettingsScreen(vm,showlable, showlablechanged = {showlablech -> showlable = showlablech},true,innerPadding, blur,blurchanged = {blurch -> blur = blurch})
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun texts(vm : LoginSuccessViewModel,num : Int) : String {
    when(num){
        0 -> {
            val dayweek = GetDate.dayweek
            var chinesenumber  = GetDate.chinesenumber

            when (dayweek) {
                1 -> chinesenumber = "一"
                2 -> chinesenumber = "二"
                3 -> chinesenumber = "三"
                4 -> chinesenumber = "四"
                5 -> chinesenumber = "五"
                6 -> chinesenumber = "六"
                0 -> chinesenumber = "日"
            }
            return "今天  第${Benweeks}周  周$chinesenumber  $Date_MM_dd"
        }
        1 -> {
            val dayweek = GetDate.dayweek
            var chinesenumber  = GetDate.chinesenumber

            when (dayweek) {
                1 -> chinesenumber = "一"
                2 -> chinesenumber = "二"
                3 -> chinesenumber = "三"
                4 -> chinesenumber = "四"
                5 -> chinesenumber = "五"
                6 -> chinesenumber = "六"
                0 -> chinesenumber = "日"
            }
            return "今天  第${Benweeks}周  周$chinesenumber  $Date_MM_dd"
        }
        2 -> {
            var text  = "你好"
            if(GetDate.formattedTime.toInt() == 12) text = "午饭时间到~"
            if(GetDate.formattedTime.toInt() in 13..17) text = "下午要忙什么呢"
            if(GetDate.formattedTime.toInt() in 7..11) text = "上午好呀"
            if(GetDate.formattedTime.toInt() in 5..6) text = "起的好早呀"
            if(GetDate.formattedTime.toInt() in 18..23) text = "晚上好"
            if(GetDate.formattedTime.toInt() in 0..4) text = "熬夜也要早睡哦"

            return "$text ${getName(vm)} 同学"
        }
        3 -> {
            return "肥工教务通 选项"
        }
        else -> return "肥工教务通"
    }

}