package com.hfut.schedule.ui.activity.home.search.functions.dormitoryScore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.R
import com.hfut.schedule.viewmodel.NetWorkViewModel
import com.hfut.schedule.ui.utils.Round
import com.hfut.schedule.ui.utils.ScrollText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DormitoryScoreXuanCheng(vm : NetWorkViewModel) {
    vm.XuanquData.value = "{}"
    val sheetState_Xuanqu = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet_Xuanqu by remember { mutableStateOf(false) }


    ListItem(
        headlineContent = { ScrollText(text = "寝室评分") },
        leadingContent = { Icon(painter = painterResource(R.drawable.psychiatry),"" ) },
        modifier = Modifier.clickable { showBottomSheet_Xuanqu = true }
    )

    if (showBottomSheet_Xuanqu) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet_Xuanqu = false
            },
            sheetState = sheetState_Xuanqu,
            shape = Round(sheetState_Xuanqu)
        ) {
            Column() {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) { DormitoryScoreUI(vm) }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}