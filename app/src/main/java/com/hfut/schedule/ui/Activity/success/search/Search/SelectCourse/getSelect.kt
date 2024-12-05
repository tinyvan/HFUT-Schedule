package com.hfut.schedule.ui.Activity.success.search.Search.SelectCourse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hfut.schedule.ViewModel.NetWorkViewModel
import com.hfut.schedule.logic.datamodel.Jxglstu.SelectCourse
import com.hfut.schedule.logic.datamodel.Jxglstu.SelectCourseInfo

fun getSelectCourseList(vm : NetWorkViewModel) : List<SelectCourse> {
    val json = vm.selectCourseData.value
    val courses: List<SelectCourse> = Gson().fromJson(json, object : TypeToken<List<SelectCourse>>() {}.type)
    return courses
}
fun getSelectCourseInfo(vm : NetWorkViewModel) : List<SelectCourseInfo>  {
    val json = vm.selectCourseInfoData.value
    val courses: List<SelectCourseInfo> = Gson().fromJson(json, object : TypeToken<List<SelectCourseInfo>>() {}.type)
    return courses
}
fun getSelectedCourse(vm : NetWorkViewModel) : List<SelectCourseInfo>  {
    val json = vm.selectedData.value
    val courses: List<SelectCourseInfo> = Gson().fromJson(json, object : TypeToken<List<SelectCourseInfo>>() {}.type)
    return courses
}