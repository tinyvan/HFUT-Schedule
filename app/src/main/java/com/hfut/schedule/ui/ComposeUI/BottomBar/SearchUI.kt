package com.hfut.schedule.ui.ComposeUI.BottomBar

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.JxglstuViewModel
import com.hfut.schedule.activity.FWDTLoginActivity
import com.hfut.schedule.activity.LoginSuccessAcitivity
import com.hfut.schedule.ui.ComposeUI.emptyRoomUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(vm : JxglstuViewModel) {
    var loading by remember { mutableStateOf(true) }




    if (loading) {
        LaunchedEffect(Unit) {
            delay(500)
           //在此插入课程表的布局，加载完成后显示//在此插入课程表的UI填充操作
            loading = false

        }
    }
    //待开发
    // 考试安排 //培养方案 //空教室 //一卡通
    val prefs = MyApplication.context.getSharedPreferences("com.hfut.schedule_preferences", Context.MODE_PRIVATE)
    val cookie = prefs.getString("redirect", "")

    CoroutineScope(Job()).apply {

        launch {
            vm.getProgram(cookie!!)
            vm.getExam(cookie!!)
        }
        launch {
        delay(500)
            if (vm.token.value != null) {
            vm.getCard()
            vm.getBorrowBooks()
            vm.getSubBooks()
         //   vm.selectBuilding()
            }
            else Toast.makeText(MyApplication.context,"超时,再试一次",Toast.LENGTH_SHORT).show()

        }

    }


    val card =prefs.getString("card","未获取到")
    val borrow =prefs.getString("borrow","未获取到")
    val sub =prefs.getString("sub","未获取到")


    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var view by rememberSaveable { mutableStateOf("") }

    val sheetState2 = rememberModalBottomSheetState()
    val scope2 = rememberCoroutineScope()
    var showBottomSheet2 by remember { mutableStateOf(false) }


    val sheetState3 = rememberModalBottomSheetState()
    val scope3 = rememberCoroutineScope()
    var showBottomSheet3 by remember { mutableStateOf(false) }




 //  var examcourseNmae by rememberSaveable { mutableStateOf("") }
 //   var examtime by rememberSaveable { mutableStateOf("") }
    //var examroom by rememberSaveable { mutableStateOf("") }


    fun ExamGet() : List<Map<String,String>>{
        //考试JSON解析
        val nulls = "        <tbody>\n" +
                "          <tr>\n" +
                "            <td>空</td>\n" +
                "            <td class=\"time\">空</td>\n" +
                "            <td>空</td>\n" +
                "          </tr>\n" +
                "        </tbody>"

        val examjson = prefs.getString("exam", nulls)

        val doc = Jsoup.parse(examjson).select("tbody tr")

        val data = doc.map { row ->
            val elements = row.select("td")
            val courseName = elements[0].text()
            val examRoom = elements[2].text()
            val  examtime = elements[1].text()
            mapOf("课程名称" to courseName,
                "日期时间" to examtime,
                "考场" to examRoom)
        }
        return data
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("查询中心") }
            )
        },) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            //.background()插入背景
        ) {


            AnimatedVisibility(
                visible = loading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column() { CircularProgressIndicator() }

                }
            }//加载动画居中，3s后消失

            AnimatedVisibility(
                visible = !loading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Spacer(modifier = Modifier.height(15.dp))

                    ListItem(
                        headlineContent = { Text(text = "成绩单") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.article),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {
                            val grade = prefs.getString("grade", "")
                            showBottomSheet = true
                            view = "空"
                        }
                    )

                    ListItem(
                        headlineContent = { Text(text = "考试查询") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.draw),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {

                            showBottomSheet3 = true

                         ExamGet()
                        }
                    )




                    ListItem(
                        headlineContent = { Text(text = "培养方案") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.conversion_path),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {
                            val program = prefs.getString("program", " <h2 class=\"info-title\"><i style=\"color: #ffa200;\" class=\"fa fa-warning highlight\"></i>未获取到</h2>")
                            showBottomSheet = true
                            val doc = Jsoup.parse(program)
                            view = doc.select("h2.info-title").text()
                        }
                    )

                    ListItem(
                        headlineContent = { Text(text = "空教室") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.meeting_room),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {
                            showBottomSheet2 = true
                            vm.searchEmptyRoom("XC001")
                            vm.searchEmptyRoom("XC002")
                           // view = "待开发"


                        }
                    )

                    ListItem(
                        headlineContent = { Text(text = "一卡通余额   ${card} 元") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.credit_card),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {

                        }
                    )




                    ListItem(
                        headlineContent = { Text(text = "图书服务   借阅 ${borrow} 本   预约 ${sub} 本") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.book),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {

                        }
                    )

                    ListItem(
                        headlineContent = { Text(text = "本学期课程") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.calendar_view_month),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {
                            showBottomSheet = true
                            view = "暂未开发"
                        }
                    )

                    ListItem(
                        headlineContent = { Text(text = "全校课表查询") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.travel_explore),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {
                            Toast.makeText(MyApplication.context, "暂未开发", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )

                    ListItem(
                        headlineContent = { Text(text = "服务大厅") },
                        leadingContent = {
                            Icon(
                                painterResource(R.drawable.redeem),
                                contentDescription = "Localized description",
                            )
                        },
                        modifier = Modifier.clickable {
                            Toast.makeText(MyApplication.context, "暂未开发", Toast.LENGTH_SHORT)
                                .show()
                            val it = Intent(MyApplication.context, FWDTLoginActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            MyApplication.context.startActivity(it)
                        }
                    )





                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            },
                            sheetState = sheetState
                        ) {
                            Column() {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(text = view)
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }


                        }
                    }
                    if (showBottomSheet2) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet2 = false
                            },
                            sheetState = sheetState2
                        ) {
                            Column() {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    emptyRoomUI(vm)
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }


                        }
                    }
                    if (showBottomSheet3) {

                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet3 = false
                            },
                            sheetState = sheetState3
                        ) {
                           LazyColumn {
                               items(ExamGet()) {item ->

                                   Row(
                                       modifier = Modifier.fillMaxWidth(),
                                       horizontalArrangement = Arrangement.Center
                                   ) {
                                       Column() {
                                           ListItem(
                                               headlineContent = {  Text(text = "${item["课程名称"]}") },
                                               overlineContent = {Text(text = "${item["日期时间"]}")},
                                               supportingContent = { Text(text = "${item["考场"]}")},
                                               leadingContent = {
                                                   Icon(
                                                       painterResource(R.drawable.schedule),
                                                       contentDescription = "Localized description",
                                                   )
                                               },
                                               modifier = Modifier.clickable {

                                               }
                                           )

                                       }
                                   }
                               }
                           }
                        }
                    }

                }
            }
        }
    }

}