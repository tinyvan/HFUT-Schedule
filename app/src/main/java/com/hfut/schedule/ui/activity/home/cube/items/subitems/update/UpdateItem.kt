package com.hfut.schedule.ui.activity.home.cube.items.subitems.update

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfut.schedule.R
import com.hfut.schedule.logic.utils.APPVersion
import com.hfut.schedule.ui.utils.DividerText
import com.hfut.schedule.ui.utils.MyToast
import com.hfut.schedule.ui.utils.ScrollText


@SuppressLint("SuspiciousIndentation")
@Composable
fun VersionInfoCard() {
    if(APPVersion.getVersionName().contains("Preview")) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
            shape = MaterialTheme.shapes.medium,
        ) { ListItem(headlineContent = { Text(text = APPVersion.getVersionName(), fontSize = 28.sp) }) }
    } else
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            ListItem(headlineContent = { Text(text = "聚在工大 " + APPVersion.getVersionName(), fontSize = 28.sp) })
            Row {
                ListItem(
                    overlineContent = { Text(text = "2024-12-29") },
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.sdk), contentDescription = "") },
                    headlineContent = { ScrollText(text = "第${APPVersion.getVersionCode()}次更新") },
                    modifier = Modifier.weight(.5f)
                )
                ListItem(
                    headlineContent = { ScrollText(text = "Chiu-xaH") },
                    overlineContent = { Text(text = "构建") },
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.support), contentDescription = "") },
                    modifier = Modifier.weight(.5f).clickable {
                        MyToast("想成为下个版本的构建者吗?跟随 设置-维护关于-开源主页")
                    }
                )
            }
        }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun VersionInfo() {
    VersionInfoCard()
    DividerText(text = "新特性")
    UpdateItems("新增 课程表方格布局可显示待考考试","期末周的课表也不再空空的了", UpdateType.ADD)
    UpdateItems("新增 聚焦首页考试天数倒计时",null, UpdateType.ADD)
    UpdateItems("新增 成绩检索",null, UpdateType.ADD)
    UpdateItems("重构 原圆圈加载动画修改为新的几何图动画",null, UpdateType.RENEW)
    UpdateItems("优化 成绩雷达图的显示及动画",null, UpdateType.OPTIMIZE)
    UpdateItems("优化 成绩的刷新机制",null, UpdateType.OPTIMIZE)
    UpdateItems("优化 冷启动时的权限请求",null, UpdateType.OPTIMIZE)
    UpdateItems("优化 教评功能的界面引导",null, UpdateType.OPTIMIZE)
    UpdateItems("优化 课程汇总对课程进度的分类显示",null, UpdateType.OPTIMIZE)
    UpdateItems("优化 部分界面加载完成后的过渡衔接动画",null, UpdateType.OPTIMIZE)
    UpdateItems("优化 课程表的方格颜色",null, UpdateType.OPTIMIZE)
}

enum class UpdateType {
    //新增
    ADD,
    //下线
    DEGREE,
    //优化
    OPTIMIZE,
    //修复
    FIX,
    //重构
    RENEW,
    //其他
    OTHER
}
@Composable
fun UpdateItems(title : String,info : String?,type : UpdateType) {
    ListItem(
        headlineContent = { Text(text = title) },
        supportingContent = { info?.let { Text(text = it) } },
        leadingContent = { Icon(painter = painterResource(id = updateTypeIcons(type)), contentDescription = "") }
    )
}

fun updateTypeIcons(type : UpdateType) : Int {
    return when(type) {
        UpdateType.ADD -> R.drawable.add_circle
        UpdateType.DEGREE -> R.drawable.do_not_disturb_on
        UpdateType.OPTIMIZE -> R.drawable.tune
        UpdateType.FIX -> R.drawable.build
        UpdateType.RENEW -> R.drawable.alt_route
        UpdateType.OTHER -> R.drawable.stacks
    }
}

