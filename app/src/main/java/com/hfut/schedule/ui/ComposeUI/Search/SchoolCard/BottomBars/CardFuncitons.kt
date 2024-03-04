package com.hfut.schedule.ui.ComposeUI.Search.SchoolCard.BottomBars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.App.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.logic.utils.GetDate
import com.hfut.schedule.logic.utils.OpenAlipay
import com.hfut.schedule.logic.utils.SharePrefs
import com.hfut.schedule.ui.ComposeUI.Search.SchoolCard.CardLimit
import com.hfut.schedule.ui.ComposeUI.Search.SchoolCard.SearchBillsUI
import com.hfut.schedule.ui.ComposeUI.Search.SchoolCard.SelecctDateRange
import com.hfut.schedule.ui.UIUtils.MyToast
import java.math.BigDecimal
import java.math.RoundingMode


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardFunctions(vm : LoginSuccessViewModel,innerPaddings : PaddingValues) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPaddings)) {



        val sheetState_Range = rememberModalBottomSheetState()
        var showBottomSheet_Range by remember { mutableStateOf(false) }

        val sheetState_Search = rememberModalBottomSheetState()
        var showBottomSheet_Search by remember { mutableStateOf(false) }

        val sheetState_Settings = rememberModalBottomSheetState()
        var showBottomSheet_Settings by remember { mutableStateOf(false) }

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
            ) { CardLimit(vm) }
        }




        Spacer(modifier = Modifier.height(5.dp))

        CardRow(vm,true)

        Spacer(modifier = Modifier.height(5.dp))

        ListItem(
            headlineContent = { Text(text = "充值跳转") },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.add_card), contentDescription = "")},
            modifier = Modifier.clickable { OpenAlipay.openAlipay(MyApplication.AlipayCardURL) }
        )
        ListItem(
            headlineContent = { Text(text = "范围支出") },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.settings_ethernet), contentDescription = "")},
            modifier = Modifier.clickable { showBottomSheet_Range = true }
        )
        ListItem(
            headlineContent = { Text(text = "搜索账单") },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")},
            modifier = Modifier.clickable { showBottomSheet_Search = true }
        )
        ListItem(
            headlineContent = { Text(text = "限额修改") },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.price_change), contentDescription = "")},
            modifier = Modifier.clickable { showBottomSheet_Settings = true }
        )
        ListItem(
            headlineContent = { Text(text = "挂失解挂") },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.error), contentDescription = "")},
            modifier = Modifier.clickable { MyToast("暂未开发") }
        )
        ListItem(
            headlineContent = { Text(text = "出账提醒") },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.notifications), contentDescription = "")},
            modifier = Modifier.clickable { MyToast("暂未开发") }
        )
    }
}


@Composable
fun CardRow(vm : LoginSuccessViewModel,show : Boolean) {
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
    val now = SharePrefs.prefs.getString("card_now","0.0")
    val settle = SharePrefs.prefs.getString("card_settle","0.0")
    val num = todaypay.toString()
    val bd = BigDecimal(num)
    val str = bd.setScale(2, RoundingMode.HALF_UP).toString()

    //添加间距
    Spacer(modifier = Modifier.height(5.dp))


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
        shape = MaterialTheme.shapes.medium
    ) {
        Row {
            ListItem(
                headlineContent = { Text(text = "余额 ￥${now}") },
                modifier = Modifier.width(185.dp),
                supportingContent = { Text(text = "待圈存 ￥${settle}") },
                leadingContent = { Icon(painterResource(R.drawable.account_balance_wallet), contentDescription = "Localized description",) },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
            ListItem(
                headlineContent = { Text(text = "￥${str}") },
                supportingContent = { Text(text = " 今日消费") },
                leadingContent = { Icon(painterResource(R.drawable.send_money), contentDescription = "Localized description",) },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
        if(show) {
            Row {
                ListItem(
                    headlineContent = { Text(text = "今日账单") },
                    supportingContent = { Text(text = "点击查看")},
                    modifier = Modifier.width(185.dp).clickable { MyToast("正在开发") },
                    leadingContent = { Icon(painterResource(R.drawable.monetization_on), contentDescription = "Localized description",) },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
                ListItem(
                    headlineContent = { Text(text = "￥XX.XX") },
                    supportingContent = { Text(text = " 本月支出") },
                    leadingContent = { Icon(painterResource(R.drawable.credit_card), contentDescription = "Localized description",) },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
    }
}