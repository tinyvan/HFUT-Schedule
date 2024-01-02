package com.hfut.schedule.ui.ComposeUI.Settings.Items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.R
import com.hfut.schedule.logic.utils.SharePrefs
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.ui.UIUtils.MyToast
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestArrange() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("请求范围") },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ){
            LazyColumn {
                item { ArrangeItem(Title = "图书检索", Icon = R.drawable.book, SaveTitle = "BookRequest") }
                item { ArrangeItem(Title = "一卡通", Icon = R.drawable.credit_card, SaveTitle = "CardRequest") }
                item { ArrangeItem(Title = "挂科率", Icon = R.drawable.monitoring, SaveTitle = "FailRateRequest") }
            }
        }
    }
}


@Composable
fun ArrangeItem(Title : String,Icon : Int,SaveTitle : String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = MaterialTheme.shapes.medium
    ){
        var prefss = prefs.getString(SaveTitle,"15")
        var sliderPosition by remember { mutableStateOf(prefss!!.toFloat()) }
        val bd = BigDecimal(sliderPosition.toString())
        val str = bd.setScale(0, RoundingMode.HALF_UP).toString()
      //  var Num by remember { mutableStateOf(sliderPosition.toString()) }
        ListItem(
            headlineContent = { Text(text = "${Title}   ${str} 条/页")},
            leadingContent = { Icon(painterResource(id = Icon), contentDescription = "") }
        )

        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                val bd = BigDecimal(sliderPosition.toString())
                val str = bd.setScale(0, RoundingMode.HALF_UP).toString()
                SharePrefs.Save(SaveTitle,str)
                            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 24,
            valueRange = 5f..30f,
            modifier = Modifier.padding(horizontal = 25.dp)
        )
    }
}