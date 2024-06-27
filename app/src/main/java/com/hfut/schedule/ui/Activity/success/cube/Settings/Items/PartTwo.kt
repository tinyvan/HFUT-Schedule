package com.hfut.schedule.ui.Activity.success.cube.Settings.Items

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfut.schedule.App.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.activity.FixActivity
import com.hfut.schedule.logic.dao.dataBase
import com.hfut.schedule.logic.utils.APPVersion
import com.hfut.schedule.logic.utils.ShareAPK.ShareAPK
import com.hfut.schedule.logic.utils.SharePrefs.SaveBoolean
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.logic.utils.StartApp.StartUri
import com.hfut.schedule.ui.Activity.success.cube.Settings.Monet.MonetColorItem
import com.hfut.schedule.ui.Activity.success.cube.Settings.getUpdates
import com.hfut.schedule.ui.UIUtils.LittleDialog

fun Clear() {
    val dbwritableDatabase =  dataBase.writableDatabase
    dbwritableDatabase.delete("Book",null,null)
    val prefs = MyApplication.context.getSharedPreferences("com.hfut.schedule_preferences", Context.MODE_PRIVATE)
    prefs.edit().clear().commit()
    Toast.makeText(MyApplication.context,"已清除缓存和数据库", Toast.LENGTH_SHORT).show()
    //崩溃操作
    val s = listOf("")
    println(s[2])
}
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartTwo() {

    val Saveselect = prefs.getBoolean("select",false)
    var select by remember { mutableStateOf(Saveselect) }

    var automode by remember { mutableStateOf(true) }
    var mode by remember { mutableStateOf(true) }

    MonetColorItem()


  //  ListItem(
    //    headlineContent = { Text(text = "主题色模式") },
      //  supportingContent = {
        //    if(automode)
         //   Text(text = "已跟随系统,关闭开关可自定义")
           // else {
             //   Column {
               //     Text(text = "选择深浅色主题,打开开关将跟随系统")
             //       Row {
               //         FilterChip(
                 //           onClick = {
                   //             select = true
                     //           SaveBoolean("select",false,select)
                       //     },
                         //   label = { Text(text = "深色") }, selected = select)
                       // Spacer(modifier = Modifier.width(10.dp))
                       // FilterChip(
                         //   onClick = {
                           //     select = false
                             //   SaveBoolean("select",false,select)
                          //  },
                //            label = { Text(text = "浅色") }, selected = !select)
                 //   }
               // }
          ///  }
       // },
       // trailingContent = { Switch(enabled = false,checked = automode, onCheckedChange = { modech -> automode = modech }) },
     //   leadingContent = {
       //     Icon(
         //       painterResource(if(automode)R.drawable.routine else if(mode) R.drawable.light_mode else R.drawable.dark_mode ),
           //     contentDescription = "Localized description",
           // )
       // },
       // modifier = Modifier.clickable{}
   // )



    var version by remember { mutableStateOf(getUpdates()) }
    var showBadge by remember { mutableStateOf(false) }
    if (version.version != APPVersion.getVersionName()) showBadge = true


    ListItem(
        headlineContent = { Text(text = "获取更新") },
        supportingContent = { Text(text = if(version.version == APPVersion.getVersionName()) "当前为最新版本 ${APPVersion.getVersionName()}" else "当前版本  ${APPVersion.getVersionName()}\n最新版本  ${version.version}") },
        leadingContent = {
            BadgedBox(badge = {
                if(showBadge)
                    Badge(modifier = Modifier.size(7.dp)) }) {
                Icon(painterResource(R.drawable.arrow_upward), contentDescription = "Localized description",)
            }
        },
        modifier = Modifier.clickable{
            if (version.version != APPVersion.getVersionName())
                StartUri("https://gitee.com/chiu-xah/HFUT-Schedule/releases/tag/Android")
            else Toast.makeText(MyApplication.context,"与云端版本一致",Toast.LENGTH_SHORT).show()
        }
    )
    ListItem(
        headlineContent = { Text(text = "用户统计数据") },
        supportingContent = { Text(text = "允许上传非敏感数据,以帮助更好的改进体验") },
        leadingContent = { Icon(painterResource(R.drawable.cloud_upload), contentDescription = "Localized description",) },
        trailingContent = {Switch(checked = false, onCheckedChange = {}, enabled = false)}
    )

   // ListItem(
     //   headlineContent = { Text(text = "降级到3.X版本 (4.0 限定选项)") },
      //  supportingContent = { Text(text = "由于4.0初期存在若干Bug,如影响使用,可点击此选项获取上版")},
       // leadingContent = { Icon(painterResource(R.drawable.trending_down), contentDescription = "Localized description",) },
        //modifier = Modifier.clickable{ StartUri("https://gitee.com/chiu-xah/HFUT-Schedule/releases/tag/Memory") }
    //)




    ListItem(
        headlineContent = { Text(text = "关于与修复") },
        supportingContent = { Text(text = "当出现问题时,可从此处进入或长按桌面图标选择修复")},
        leadingContent = { Icon(painterResource(R.drawable.build), contentDescription = "Localized description",) },
        modifier = Modifier.clickable{
            val it = Intent(MyApplication.context, FixActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            MyApplication.context.startActivity(it)
        }
    )
}

@Composable
fun UpdateUI() {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = MaterialTheme.shapes.medium

    ){
        UpdateItem()
    }
}

@Composable
fun UpdateItem() {
    var version by remember { mutableStateOf(getUpdates()) }

    var expandItems by remember { mutableStateOf(false) }
    ListItem(
        headlineContent = { Text(text = "发现新版本") },
        supportingContent = { Text(text = "${APPVersion.getVersionName()} → ${version.version}") },
        leadingContent = { Icon(painterResource(R.drawable.arrow_upward), contentDescription = "Localized description",) },
        trailingContent = {
            IconButton(onClick = { expandItems = !expandItems }) { Icon(painterResource(id = if(!expandItems) R.drawable.expand_content else R.drawable.collapse_content), contentDescription = "")
            }
            },
        modifier = Modifier.clickable{ StartUri("https://gitee.com/chiu-xah/HFUT-Schedule/releases/tag/Android") },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.errorContainer)
    )

    AnimatedVisibility(
        visible = expandItems,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + scaleIn(
            transformOrigin = TransformOrigin(0.5f, 0f)
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)) {
        ListItem(
            headlineContent = { Text(text ="更新日志") },
            supportingContent = {
                getUpdates().text?.let { Text(text = " $it") }
            },
            leadingContent = { Icon(painter = painterResource(id = R.drawable.hotel_class), contentDescription = "")},
            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.errorContainer)
        )
    }
}