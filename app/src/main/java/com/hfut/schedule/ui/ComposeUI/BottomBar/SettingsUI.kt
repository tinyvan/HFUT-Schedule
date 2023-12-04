package com.hfut.schedule.ui.ComposeUI.BottomBar

import android.annotation.SuppressLint
import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.ui.ComposeUI.Search.LibraryItem
import com.hfut.schedule.ui.ComposeUI.Search.XuanquItem
import com.hfut.schedule.ui.ComposeUI.Settings.MyAPIItem
import com.hfut.schedule.ui.ComposeUI.Settings.MonetColorItem
import com.hfut.schedule.ui.ComposeUI.Settings.SettingsItems
import com.hfut.schedule.ui.ComposeUI.Search.WebUI

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(vm : LoginSuccessViewModel,
                   showItem : Boolean,
                   showlable : Boolean,
                   showlablechanged: (Boolean) -> Unit,
                   ) {
    val sp =
        PreferenceManager.getDefaultSharedPreferences(MyApplication.context)
    if (sp.getBoolean("SWITCH", true) != showlable) { sp.edit().putBoolean("SWITCH", showlable).apply() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("肥工教务通") }
            )
        },) {innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()) {
            Spacer(modifier = Modifier.height(10.dp))

            MyAPIItem()

            if(showItem) {
                LibraryItem(vm)
                XuanquItem(vm)
                WebUI()
                Spacer(modifier = Modifier.height(5.dp))
               // FWDT()
                Divider()
                Spacer(modifier = Modifier.height(5.dp))
            }

            ListItem(
                headlineContent = { Text(text = "显示标签") },
                leadingContent = { Icon(painterResource(R.drawable.label), contentDescription = "Localized description",) },
                trailingContent = { Switch(checked = showlable, onCheckedChange = showlablechanged) }
            )

            MonetColorItem()

            SettingsItems()

            Spacer(modifier = Modifier.height(90.dp))

        }
    }
}