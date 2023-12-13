# HFUT-Schedule

♢更新日志: 2.4 正式版

-2.4.4.1

修复 首次登录闪退Bug

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

1.记住密码

2.获取课表

3.关于与反馈

4.离线课表


♢【肥工教务通】概述:

这是一个入门学习安卓开发后的第二个练手项目，目标是实现对接合肥工业大学学生教务系统，实时获取课程表（也因为我喜欢用原生程序而不是小程序……）

一名23届合肥工业大学宣区资环学子的作品

♢截图：

![微信图片_20231212221630](https://github.com/Chiu-xaH/HFUT-Schedule/assets/116127902/de0db8a9-cc23-4be3-b8c4-402b8ea83b7e)


♢权限：

1.网络请求

2.存储

♢待实现：

1.成绩单（目前账号无成绩，暂时无法绘制界面）

2.课程汇总(里面区分出其他课程)

4.查询电费网费等

5.校车查询

6.第二课堂

7.滑动删除

....目前就这些，如果你有更多想法可以告诉我^_^

♢待修复：

1.Bottom Sheet加载后短暂界面顿挫

♢网络接口：

1.教务系统 -> http://jxglstu.hfut.edu.cn/eams5-student/

2.信息门户 -> https://one.hfut.edu.cn/

3.慧新易校 -> http://121.251.19.62/

4.服务大厅 -> http://172.31.248.26:8088/

5.我的接口 -> https://chiu-xah.github.io/

6.图书馆 -> http://210.45.242.5:8080/

7.宣区寝室卫生查询 -> http://39.106.82.121/

8.CAS统一登陆 -> https://cas.hfut.edu.cn/

9.指尖工大 -> https://zjgd.hfut.edu.cn:8181/
