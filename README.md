# HFUT-Schedule


本应用作为本人一个练手项目，初衷想获取教务课表，随着后面需求不断增加，现在将几乎大部分合工大接口全部集成，可更便捷地查询信息等

尽管初衷是作为练手项目，但我仍很重视它，它逐渐成长，功能日趋复杂多样，任何新学知识都会随着更新被带到此APP中，旧的代码也会重构，大学的第一个学期在这个软件用了很多心思😊欢迎大家使用


♢更新日志: 4.0 正式版

-4.0

本次大版本以重构网络请求为主，重构后提高了稳定性与性能，与原版本兼容性未知，若出现问题建议清除数据

重构【教务登录】登录流程部分重构，增强了稳定性，缩短了加载时间

重构 莫奈取色 为【取色算法】，支持切换原生算法与莫奈取色算法，并优化了逻辑

重构 聚焦优先 选项为【主页面】，优化了逻辑

新增【权限申请】，完善未授予日历权限时的处理

优化 课表切换布局时的细节

优化 部分界面显示效果

优化 冗余代码

修复 登陆后课表日期行显示Bug

修复 重构课表中已知若干Bug

修复 聚焦存在潜在崩溃Bug

-3.3

新增【课表布局切换】自由切换5*与7*布局

-3.2.1.1

修复课表方格中存在点击崩溃Bug

-3.2.1

优化部分界面显示效果为瀑布流

-3.2

新增【登陆教务】选项，快速启动时有效

新增【快速启动】开关

登陆后课表的界面也已重构为新的界面

优化部分网络请求协程逻辑

优化部分界面的显示

-3.1.2

新增【成绩学期切换】

紧急修复由于月份更迭错误导致成绩不显示Bug

-3.1.1

成绩查询的教务接口回归，现在由两个接口共同服务，可查询详细成绩信息

-3.1

重构 【离线课程表】

新增 【下拉刷新】

新增 【添加到系统日历】,个人接口可用

界面革新，沉浸式可滚动布局，由有限4*5升级到沉浸5*5布局

离线课表接口更换为Community，登陆一次后可实时更新课表

-3.0.3.4

修复  一卡通消费流水为0.0X元时导致崩溃

修复  "C/C++语言程序设计"课程名称在方格中过长导致的显示不全Bug

-3.0.3.3

为下学期的课表更迭优化了逻辑

精简18%的包体

-3.0.3.2

新增读写日历权限

优化部分逻辑

优化MyAPI的界面展示,丰富了图标,增加了彩蛋

-3.0.3.1

修复 聚焦课表崩溃Bug

优化 电费查询 界面显示

-3.3.3

本来想重构完课程表后一起发布的,最近事情多,只能搁置了

更换聚焦课表接口为Community,现在聚焦内课表可免登录实时更新

新增【校历】

新增【请求范围】选项

新增 课程汇总【二级菜单】可查看更多详情信息

优化 部分界面的显示

-3.0.2

新增【查询中心刷新】按钮

修复 跨年而导致地成绩无法显示Bug

移除 部分无用控件

-3.0.1

3.0 第一个迭代版本来啦,本次重构内容依旧很多,可能存在新的崩溃或Bug问题,更新内容如下:

新增【借阅图书】

新增【添加按钮】显示开关

修复 图书检索崩溃Bug

修复 推广应用时推送链接有误

移除 降级2.X 选项

移除 学期刷新 选项

优化 聚焦界面显示

重构大部分网络加载流程，包括但不限于电费查询,寝室分数查询,图书检索,一卡通,挂科率,使用更高级的方法极大地优化加载速度和减少崩溃率

一卡通流水一页显示记录，已由8条扩充到15条，后续版本会在设置中开放自定义页数选项

适配一卡通流水 校医院图标

修复范围支出偶尔崩溃Bug

-3.0

本次更新将带来新的接口，新的接口登录一次后可保持登录状态，也就是说只需登录一次，考试，成绩等等原来需要登录才能查询或者更新的内容，可实时更新查询。

注意，本次为大更新，涉及内部架构更改，推荐重新安装，并且大改必然会出现许多Bug，如影响使用，请从选项中选择降级，获取上一版本

新的接口让旧功能得以优化，同时也带来了许多新功能，详细如下：

新增【Community】接口

新增【课程汇总】

新增【打开云运动】

新增【挂科率】

新增【清理数据库】

新增   查询中心中部分项目红点提醒

新增  【降级选项】（仅本版本限定）

去除  Beta功能 选项，默认展示

优化  查询中心标题统一性

重构【考试查询】
           
           新增 【免登录查询】
           
           优化   界面展示
          
           优化   内部逻辑，聚焦与查询同步界面
          
           更换   数据源为Community

重构【我的成绩】
          
          新增【免登录查询】
          
          新增【成绩排名】
          
          预留  总成绩 接口
         
          更换  数据源为Community

重构【图书服务】
        
          新增 【图书馆藏信息】
       
          优化   界面展示
  
          预留   借阅图书信息 接口，目前无数据，无法制作，后续做
       
          新增【借阅历史】
        
          更换   数据源为Community，不接入校园网也可查询

重构【培养方案】
           
           更换数据源为 Community

           新增 【学分统计】
        
           由于我没有培养方案，没法完全做此功能，后续完善

-2.5.3.1

新增  【我的成绩】

优化     架构,进一步解耦合

-2.5.3

新增Beta【热水机】(仅支持宣区六号六楼)

新增【添加本地聚焦】,可以自行添加删除聚焦信息

新增【下拉刷新】,聚焦界面下拉

聚焦相关选项升级集成为【聚焦编辑】,增加若干选项

修复   年份更迭导致的错误显示Bug

修改   聚焦优先开关默认状态为开

优化  寝室分数查询的查找逻辑

优化   部分界面的过渡动画

-2.5.2.1

修复     从2.5.2以下版本更新带来的崩溃问题

优化     部分选项逻辑

精简      部分冗杂素材

-2.5.2

新增    【通知中心】

新增     【红点提醒】

新增    【推广本应用】

新增     【启动画面】

新增      潜在Bug

调整     部分界面布局与图标

去除     聚焦刷新按钮（用处不大）

-2.5.1.1

新增     【电费充值入口】

新增     【仓库切换开关】

优化      部分输入逻辑为选择逻辑

优化      部分界面布局

优化      搜索记忆

去除      冗杂按钮,精简页面

精简      应用,去除不必要内容

-2.5.1

新增 【宣区电费查询】

新增 【聚焦优先】开关

去除 服务大厅 选项

优化 部分已知Bug

调整 部分界面布局

-2.5

新增 【云运动】,可查看校园跑部分信息

新增 【云运动信息配置】

新增 【Beta 功能】开关

新增 【刷新】图标

回归 图书预约本书数

修复 图书借阅本数无法获取

修复 窄屏下布局错位界面

优化 部分界面布局与图标

-2.4.4.1

新增   【待圈存余额】

优化    界面布局

变更    一卡通余额接口

修复    首次登录闪退Bug

            本次更新后需清除数据再次登录,否则会发生数据冲突,导致崩溃

-2.4.4

新增    【学期刷新】选项

新增    【Github主页】选项

优化    开发者接口开关机制

优化     学期切换机制

优化    【获取更新】机制

优化     界面的统一性

去除      时间判定机制,应用无限期可用

-2.4.3.1

新增   【显示聚焦一卡通】开关

新增   【开发者接口】开关

修复     一卡通点击切换为第一页时无限加载Bug

修复     限额修改逻辑Bug

优化     离线模式下界面,新增查询中心免登录界面

-2.4.3

新增   【一卡通限额】

新增   【月消费折线图】

优化    界面显示与布局

优化    一卡通功能的稳定性

修复    无网络连接时登录崩溃Bug

-2.4.2

优化  莫奈取色UI

       由于遇到不可控Bug,新功能暂停一版本开发
       
-2.4.1

新增   【预设色彩】,作为原固定取色设置平替方案

新增   【网址导航】,快捷访问学校网页

新增   【流水搜索】,检索关键字查找账单

优化    整体界面,加强统一性

优化   切换周数动画

优化   内部逻辑


-2.4

取色设置  升级为 【莫奈取色】,根据壁纸自动取色

升级   部分架构与依赖

-2.3.6

新增   【宣城校区寝室卫生成绩查询】

修复    部分潜在Bug

优化    一卡通余额获取逻辑,当获取为--时切换接口

优化    页面滚动逻辑

-2.3.5

新增   【流水月份查询】

新增   【流水范围查询】

优化    流水部分图标显示

修复    流水崩溃Bug

优化    应用稳定性

-2.3.4

新增   【一卡通流水查询】

优化    部分界面显示

-2.3.3

新增   【图书检索】

调整     界面

修复     部分潜在Bug

-2.3.2

修复     登陆后课表无法翻页的Bug

优化     部分界面显示

-2.3.1.1

修复    备份Bug

调整    首页Menu，对话框替换为选项

调整    部分界面

优化    日期判定机制，过期考试不再显示

去除    显示接口 开关

-2.3.1

新增    若干个人接口，位于聚焦中

修复    部分Bug

调整    部分界面布局


-2.3.0.1

新增    离线模式下的【选项】

新增   【显示接口】开关

新增   【我的接口】，位于选项中

修复    取色Bug

修复    获取一卡通Bug，当登录失效后提醒再次登录

修复    无支付宝时跳转崩溃Bug

-2.3

新增  【课表日期显示】

新增  【聚焦一卡通余额】，直接展示一卡通余额

新增  【一卡通充值】 按钮，快速跳转支付宝充值

接入  【个人接口】，后续实时同步消息

修改    默认色彩为蓝色

优化    内部机制，不登陆也可实时查看一卡通余额

优化    协程逻辑

优化    部分界面图标与列表顺序

修复    部分潜在Bug

-2.2.3

新增  【取色设置】

-2.2.2

新增  【聚焦】，直接查看近日安排

调整  个人信息  位置

-2.2.1

优化  界面显示Bug

优化  加载时间

-2.1

新增   课表点击显示拓展信息(人数,周数,教师)

新增   【查询中心】

新增   【个人信息】

修复   多次点击登录后无法登录Bug

修复   若干空指针崩溃Bug

新增   【信息门户API】

新增   【获取更新】

调整    界面显示

调整    APP名称为 肥工教务通

从本版本2.1起,完成度和稳定度已达到标准

-2.0.5

优化架构,进一步解耦合

优化   登录机制,可点击两次登录

-2.0.4

重绘  离线课表界面

新增 【应用使用说明】右上角MENU

调整  UI细节

-2.0.3.1

修复   部分登录Bug

-2.0.3

调整  APP图标

优化  界面显示

-2.0

使用Material3和Comoose重绘大部分界面

         本版本作为大更新,所有界面重构但原有功能全部承袭

-1.1

修复  周日不显示Bug

增强  稳定性

优化  加载时长

-1.0

第一版来了，已实现如下功能

记住密码

获取课表

关于与反馈

离线课表


♢【肥工教务通】概述:

这是一个入门学习安卓开发后的一个练手项目，实现对接合肥工业大学学生教务系统，获取课程表（也因为我喜欢用原生程序而不是小程序……）

一名23届合肥工业大学宣区作品,水平有限,尽力优化

♢截图：

![微信图片_20231212221630](https://github.com/Chiu-xaH/HFUT-Schedule/assets/116127902/de0db8a9-cc23-4be3-b8c4-402b8ea83b7e)


♢权限：

1.网络请求

♢下版预告：

3.1

重构 【登录】,对登录的网络请求过程进行了逻辑重构,优化了加载效率并减少了崩溃率

重构【课程表】,内部逻辑与界面全部重写

         更换 接口为Community,现在课表也可免登录实时更新

         重构 界面,间距统一,并修复了不同手机显示边距不同的异常

         合并 接口,现在课程表,聚焦及课程汇总共用同一个接口数据

新增【课表形态】选项,自由选择课表方格布局,自适应*5,4*5,5*7可选择(目前只有自适应*5与4*5可用,5*7后版本启用)

新增【学期详情】,位于课程汇总内

新增 点击聚焦课表【详情信息】

优化 课程汇总二级菜单内信息

优化 多处内部请求逻辑

优化 部分界面的显示效果

上课人数 暂时下线,后版本加回


....目前就这些，如果你有更多想法可以告诉我^_^

