package com.hfut.schedule.ui.Activity.success.search.Search.Survey

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hfut.schedule.App.MyApplication
import com.hfut.schedule.R
import com.hfut.schedule.ViewModel.LoginSuccessViewModel
import com.hfut.schedule.logic.Enums.PostMode
import com.hfut.schedule.logic.datamodel.Jxglstu.PostSurvey
import com.hfut.schedule.logic.datamodel.Jxglstu.blankQuestionAnswer
import com.hfut.schedule.logic.datamodel.Jxglstu.radioQuestionAnswer
import com.hfut.schedule.logic.datamodel.Jxglstu.saveListChoice
import com.hfut.schedule.logic.datamodel.Jxglstu.saveListInput
import com.hfut.schedule.logic.utils.SharePrefs
import com.hfut.schedule.logic.utils.SharePrefs.Save
import com.hfut.schedule.logic.utils.SharePrefs.prefs
import com.hfut.schedule.ui.Activity.success.focus.Focus.AddButton
import com.hfut.schedule.ui.UIUtils.MyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun surveyInfo(id : Int,vm: LoginSuccessViewModel) {
    var loading by remember { mutableStateOf(true) }

    val cookie = SharePrefs.prefs.getString("redirect", "")


    CoroutineScope(Job()).launch{
        async { cookie?.let { vm.getSurveyToken(it,id.toString()) } }
        async{ cookie?.let { vm.getSurvey(it,id.toString()) } }.await()
        async {
            Handler(Looper.getMainLooper()).post{
                vm.surveyData.observeForever { result ->
                    if (result != null) {
                        if(result.contains("lessonSurveyLesson")) {
                            loading = false
                        }
                    }
                }
            }
        }
    }


    AnimatedVisibility(
        visible = loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            CircularProgressIndicator()
        }
    }////加载动画居中，3s后消失


    AnimatedVisibility(
        visible = !loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        surveyList(vm)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun surveyList(vm : LoginSuccessViewModel) {
    val choiceList = getSurveyChoice(vm)
    val inputList = getSurveyInput(vm)
    //saveList(vm,choiceList,inputList)
    var input by remember { mutableStateOf("好") }
    val choiceNewList = mutableListOf<radioQuestionAnswer>()
    val inputNewList = mutableListOf<blankQuestionAnswer>()

   // val scrollstate = rememberLazyListState()
   // val shouldShowAddButton by remember { derivedStateOf { scrollstate.firstVisibleItemScrollOffset == 0 } }
    
    fun postResultNormal(vm : LoginSuccessViewModel) : JsonObject {
        val surveyAssoc = getSurveyAssoc(vm)
        val lessonSurveyTaskAssoc = prefs.getInt("teacherID", 0)
        val postSurvey = PostSurvey(surveyAssoc, lessonSurveyTaskAssoc, choiceNewList, inputNewList)
        return Gson().toJsonTree(postSurvey).asJsonObject
    }
    Box() {
        LazyColumn() {
            items(inputList.size) {item ->
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    shape = MaterialTheme.shapes.medium,
                ){
                    inputNewList.add(blankQuestionAnswer(inputList[item].id,input))
                    ListItem(
                        headlineContent = { Text(text = inputList[item].title) },
                        leadingContent = { Icon(painterResource(R.drawable.article), contentDescription = "Localized description",) },
                        supportingContent = {
                            //输入框
                            TextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 15.dp),
                                value = input,
                                onValueChange = {
                                    input = it
                                    inputNewList.add(blankQuestionAnswer(inputList[item].id,input))
                                },
                                label = { Text("输入内容" ) },
                                singleLine = true,
                                shape = MaterialTheme.shapes.medium,
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent, // 有焦点时的颜色，透明
                                    unfocusedIndicatorColor = Color.Transparent, // 无焦点时的颜色，绿色
                                ),
                                // leadingIcon = { Icon( painterResource(R.drawable.search), contentDescription = "Localized description") },
                            )
                        },
                        modifier = Modifier.clickable {

                        },
                    )
                }
            }
            items(choiceList.size) {item ->
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    shape = MaterialTheme.shapes.medium,
                ){
                    ListItem(
                        headlineContent = { Text(text = choiceList[item].title) },
                        leadingContent = { Icon(painterResource(R.drawable.article), contentDescription = "Localized description",) },
                        supportingContent = {
                            val list = getOption(choiceList[item])
                            val radioOptions = list
                            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

                            Column {
                                for(i in list.indices) {
                                   // choiceNewList.add(radioQuestionAnswer(choiceList[item].id,selectedOption.name))
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(56.dp)
                                            // .weight(.1f)
                                            .selectable(
                                                selected = (list[i] == selectedOption),
                                                onClick = {
                                                    onOptionSelected(list[i])
                                                    choiceNewList.add(radioQuestionAnswer(choiceList[item].id,selectedOption.name))
                                                          },
                                                role = Role.RadioButton
                                            )
                                            .padding(horizontal = 15.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (list[i] == selectedOption),
                                            onClick = null
                                        )
                                        Text(
                                            text = list[i].score.toString() + "分-" + list[i].name,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(start = 10.dp)
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.clickable {

                        },
                    )
                }
            }
        }
    /*    AnimatedVisibility(
            visible = shouldShowAddButton,
            enter = scaleIn() ,
            exit = scaleOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                // .padding(innerPaddings)
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            if (shouldShowAddButton) {  */
                FloatingActionButton(
                    onClick = { Log.d("c",postResultNormal(vm).toString()) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        // .padding(innerPaddings)
                        .padding(horizontal = 15.dp, vertical = 15.dp)
                ) { Icon(painterResource(id = R.drawable.arrow_upward), "Add Button") }
         //   }
     //   }
    }
}

@SuppressLint("SuspiciousIndentation")
fun selectMode(vm : LoginSuccessViewModel, mode : PostMode) : Boolean {

    val cookie = SharePrefs.prefs.getString("redirect", "")
    val token = prefs.getString("SurveyCookie","")
    return when(mode) {
        PostMode.NORMAL -> {
            //vm.postSurvey("$cookie;$token", postResultNormal(vm))
            
            MyToast("提交完成")
            true
        }

        PostMode.GOOD ->  {
            vm.postSurvey("$cookie;$token", postResult(vm,true))
            MyToast("提交完成")
            true
        }

        PostMode.BAD -> {
            vm.postSurvey("$cookie;$token", postResult(vm,false))
            MyToast("提交完成")
            true
        }
    }
}

//true为好评，false为差评
fun postResult(vm: LoginSuccessViewModel,goodMode: Boolean): JsonObject {
    val surveyAssoc = getSurveyAssoc(vm)
    val lessonSurveyTaskAssoc = prefs.getInt("teacherID", 0)
    val choiceList = getSurveyChoice(vm)
    val inputList = getSurveyInput(vm)
    val choiceNewList = mutableListOf<radioQuestionAnswer>()
    val inputNewList = mutableListOf<blankQuestionAnswer>()

    for (i in choiceList.indices) {
        val id = choiceList[i].id
        // 默认拿第一个选项为好评，拿最后一个为差评
        val option = if(goodMode) choiceList[i].options[0].name else choiceList[i].options.last().name
        choiceNewList.add(radioQuestionAnswer(id, option))
    }

    for (j in inputList.indices) {
        val id = inputList[j].id
        inputNewList.add(blankQuestionAnswer(id, "好"))
    }

    // 组装数据
    val postSurvey = PostSurvey(surveyAssoc, lessonSurveyTaskAssoc, choiceNewList, inputNewList)

    // 转换为JsonObject

    return Gson().toJsonTree(postSurvey).asJsonObject
}
