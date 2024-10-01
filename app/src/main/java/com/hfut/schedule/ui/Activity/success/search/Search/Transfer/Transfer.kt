package com.hfut.schedule.ui.Activity.success.search.Search.Transfer

import android.os.Handler
import android.os.Looper
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.hfut.schedule.App.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.logic.datamodel.Jxglstu.MyApplyResponse
import com.hfut.schedule.logic.datamodel.Jxglstu.TransferData
import com.hfut.schedule.logic.datamodel.Jxglstu.TransferResponse
import com.hfut.schedule.logic.datamodel.Jxglstu.courseType
import com.hfut.schedule.logic.datamodel.MyAPIResponse
import com.hfut.schedule.logic.utils.SharePrefs
import com.hfut.schedule.ui.Activity.success.search.Search.More.Login
import com.hfut.schedule.ui.Activity.success.search.Search.Survey.teacherList
import com.hfut.schedule.ui.Activity.success.search.Search.TotalCourse.courseIcons
import com.hfut.schedule.ui.UIUtils.MyToast
import com.hfut.schedule.ui.UIUtils.Round
import com.hfut.schedule.ui.UIUtils.ScrollText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.math.BigDecimal
import java.math.RoundingMode


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transfer(ifSaved : Boolean,vm : LoginSuccessViewModel){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val sheetState_apply = rememberModalBottomSheetState()
    var showBottomSheet_apply by remember { mutableStateOf(false) }

    val sheetState_info = rememberModalBottomSheetState()
    var showBottomSheet_info by remember { mutableStateOf(false) }

    var campusId by remember { mutableStateOf(if(getCampus()?.contains("宣城") == true) CampusId.XUANCHENG else CampusId.HEFEI) }

    ListItem(
        headlineContent = { Text(text = "转专业") },
        leadingContent = { Icon(painter = painterResource(id = R.drawable.compare_arrows), contentDescription = "") },
        modifier = Modifier.clickable {
            if(ifSaved) Login() else
                showBottomSheet = true
        }
    )
    if (showBottomSheet_apply) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet_apply = false },
            sheetState = sheetState_apply,
            shape = Round(sheetState_apply)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("转专业申请") },
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    MyApply(vm)
                }
            }
        }
    }

    if (showBottomSheet_info) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet_info = false },
            sheetState = sheetState_info,
            shape = Round(sheetState_info)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("说明") },
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    TransferTips()
                }
            }
        }
    }


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            shape = Round(sheetState)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { ScrollText("转专业") },
                        actions = {
                            Row(modifier = Modifier.padding(horizontal = 15.dp)) {
                                FilledTonalIconButton(onClick = { showBottomSheet_info = true }) {
                                    Icon(painterResource(id = R.drawable.info), contentDescription = "")
                                }
                                FilledTonalButton(
                                    onClick = {
                                    campusId = if(campusId == CampusId.XUANCHENG) CampusId.HEFEI else CampusId.XUANCHENG
                                              },
                                    ) {
                                    Text(text = if(campusId == CampusId.XUANCHENG) "宣城" else "合肥")
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                FilledTonalButton(onClick = { showBottomSheet_apply = true }) {
                                    Text(text = "我的申请")
                                }
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    TransferUI(vm,campusId)
                }
            }
        }
    }
}

@Composable
fun TransferTips() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        ListItem(
            headlineContent = { Text("具体要求") },
            supportingContent = { Text("请关注所在系QQ群或在 查询中心-新闻公告 检索已公示的转专业要求") },
        )
        ListItem(
            headlineContent = { Text("录取通知") },
            supportingContent = { Text("请关注所在转专业QQ群(上条要求会公示)或在 查询中心-新闻公告 检索已公示的面试/拟录取名单") },
        )
    }
}
