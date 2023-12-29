package com.hfut.schedule.ui.ComposeUI.Search.Grade

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.logic.utils.GetDate
import com.hfut.schedule.logic.utils.SharePrefs.Save
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.ui.ComposeUI.Search.FailRate.Click
import com.hfut.schedule.ui.ComposeUI.Search.FailRate.FailRateUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Grade(vm : LoginSuccessViewModel)  {
    val cookie = prefs.getString("redirect", "")
    val sheetState_Grade = rememberModalBottomSheetState()
    var showBottomSheet_Grade by remember { mutableStateOf(false) }
    val CommuityTOKEN = prefs.getString("TOKEN","")
    var term = ""
    val month = GetDate.Date_MM.toInt()
    if( month >= 8 || month <= 1) term = "1"
    else term = "2"
    CommuityTOKEN?.let { vm.getGrade(it,GetDate.Date_yyyy+"-"+(GetDate.Date_yyyy.toInt()+1),term) }

    ListItem(
        headlineContent = { Text(text = "成绩") },
        leadingContent = {
            BadgedBox(badge = {
                if(prefs.getString("GradeNum","0") != getGrade().size.toString())
                    Badge {
                        Text(text = getGrade().size.toString())
                    }
            }) { Icon(painterResource(R.drawable.article), contentDescription = "Localized description",) }
                         },
        modifier = Modifier.clickable {
            getGrade()
            Save("GradeNum", getGrade().size.toString())
            showBottomSheet_Grade = true
        }
    )


    if (showBottomSheet_Grade) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet_Grade = false },
            sheetState = sheetState_Grade
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text("成绩") }
                    )
                },) {innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ){
                    GradeItemUI()
                }
            }
        }
    }
}


