package com.hfut.schedule.logic.utils

object AndroidVersion {
    // 获取当前系统的API级别
    val sdkInt = android.os.Build.VERSION.SDK_INT
    // 获取当前系统的版本号
    val release = android.os.Build.VERSION.RELEASE
    // 判断是否为安卓13
    val AndroidS = android.os.Build.VERSION_CODES.S
    val AndroidR = android.os.Build.VERSION_CODES.R
}