package com.hfut.schedule.ui.Activity.success.search.Search.TotalCourse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.logic.datamodel.Community.CourseTotalResponse
import com.hfut.schedule.logic.datamodel.Community.courseBasicInfoDTOList
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.ui.UIUtils.Round
import com.hfut.schedule.ui.UIUtils.ScrollText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseTotal(vm :LoginSuccessViewModel) {
    val sheetState_Total = rememberModalBottomSheetState()
    var showBottomSheet_Total by remember { mutableStateOf(false) }
    val CommuityTOKEN = prefs.getString("TOKEN","")
    val json = prefs.getString("courses","")

    ListItem(
        headlineContent = { Text(text = "课程汇总") },
        leadingContent = {
            Icon(
                painterResource(R.drawable.category),
                contentDescription = "Localized description",
            )
        },
        modifier = Modifier.clickable {
             showBottomSheet_Total = true
        }
    )

    if (showBottomSheet_Total) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet_Total = false
            },
            sheetState = sheetState_Total,
            shape = Round(sheetState_Total)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("课程汇总") }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ){
                    CourseTotalUI(json,false)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

fun periodsSum() : Double {
    var num = 0.0
    val json = prefs.getString("courses","")
    for(i in 0 until getTotalCourse(json).size) {
        val credit = getTotalCourse(json)[i].course.credits
        if (credit != null) {
            num += credit
        }
    }
    return num
}


@Composable
fun SemsterInfo(json : String?) {

    val semsterInfo = getTotalCourse(json)[0].semester

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Column() {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 15.dp,
                        vertical = 5.dp
                    ),
                shape = MaterialTheme.shapes.medium,
            ){
                ListItem(
                     overlineContent = { Text(text = semsterInfo.startDate + " ~ " + semsterInfo.endDate)},
                    headlineContent = {  ScrollText(semsterInfo.nameZh) },
                    leadingContent = { Icon(
                        painterResource(R.drawable.category),
                        contentDescription = "Localized description",
                    ) },
                    modifier = Modifier.clickable {},
                    colors = ListItemDefaults.colors(MaterialTheme.colorScheme.primaryContainer),
                    trailingContent = { if (json != null) { if(json.contains("lessonIds"))Text(text = "学分 ${periodsSum()}") } }
                )
            }
        }
    }
}