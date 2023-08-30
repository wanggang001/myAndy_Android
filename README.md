# myAndy_Android

🚀 myAndy_Android是一个基于 **组件化+模块化+Kotlin+协程+Flow+Retrofit+Jetpack+MVVM+短视频** 架构实现的 WanAndroid 客户端。 能提供大家学习如何从0到1打造一个符合[大型Android项目的架构模式](https://juejin.cn/post/7223767530981867557)。

|                             项目截图                             |                             项目截图                             |                             项目截图                             |                             项目截图                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![首页](https://user-images.githubusercontent.com/20841967/233286288-c648a7b5-7b0f-4481-81aa-047ed2a67c73.jpeg) | ![分类](https://user-images.githubusercontent.com/20841967/233286273-873501d5-f484-4d05-8eb0-8741acd28b1e.jpeg) | ![体系](https://user-images.githubusercontent.com/20841967/233286299-5b8036bd-573f-4c78-8a8e-603063298b10.jpeg) | ![我的](https://user-images.githubusercontent.com/20841967/233286293-04e9c0fe-e0d9-49bb-b572-a055fc697a25.jpeg) |
| ![首页视频](https://user-images.githubusercontent.com/20841967/233398135-45da89b4-bbe9-4cc9-9486-acb67f88d905.gif) | ![分类体系](https://user-images.githubusercontent.com/20841967/233252110-ebc4fdc0-132a-451a-8f27-29ea92052edf.gif) | ![搜索](https://user-images.githubusercontent.com/20841967/233252130-59da489b-0595-4e8d-87cc-c9c1805ece1b.gif) | ![登录](https://user-images.githubusercontent.com/20841967/233252123-18ed07a7-89bc-42f8-a57d-7a1d4e388b09.gif) |

## 一、 关于SumTea

### 1. 项目架构
1. 项目采用 Kotlin 语言编写，结合 Jetpack 相关控件，`Navigation`，`Lifecyle`，`DataBinding`，`LiveData`，`ViewModel`等搭建的 **MVVM** 架构模式；
2. 通过**组件化**，**模块化**拆分，实现项目更好解耦和复用，[ARouter](https://github.com/alibaba/ARouter) 实现模块间通信；
3. 使用 **协程+Flow+Retrofit+OkHttp** 优雅地实现网络请求；
4. 通过 **mmkv**，**Room** 数据库等实现对数据缓存的管理；
5. 使用谷歌 **ExoPlayer** 实现短视频播放；
6. 使用 **Glide** 完成图片加载；
7. 通过 **WanAndroid** 提供的 API 实现的一款玩安卓客户端。

![SumTea工程架构](https://user-images.githubusercontent.com/20841967/233055330-89e2bdd6-3111-4726-bf5a-e77aa2ebdc2a.png)

项目使用**MVVM架构模式**，基本上遵循 Google 推荐的架构，对于 `Repository`，Google 认为 `ViewModel` 仅仅用来做数据的存储，数据加载应该由 `Repository` 来完成。通过 **Room** 数据库实现对数据的缓存，在无网络或者弱网的情况下优先展示缓存数据。
![MVVM架构图](https://user-images.githubusercontent.com/20841967/233261753-86bc4cb6-3681-49a5-a127-34d87a46d268.jpg)

### 2. 项目功能

#### APP壳工程：
1. Application类
2. 打包环境，签名，混淆规则，业务模块集成，APP 主题等配置等
#### mod_main：
1. 包含首页，分类，体系，我的四个tab，Navigation实现管理
2. 首页tab包含Banner轮播图，视频列表，项目文章列表
3. 分类tab包含各网站文章的分类内容
4. 体系tab包含知识体系的文章分类，二级为文章列表
5. 我的tab包含个人信息，服务栏，文章推荐列表
#### mod_user：
1. 包含个人设置页面，账户安全
2. 个人信息页设置头像、姓名、手机号码等个人信息
3. 隐私政策条款功能
4. 查看版本信息以及更新App功能
5. 清除手机缓存功能
6. 用户退出登录功能
#### mod_login：
1. 登录页面登录功能，以及隐私政策
2. 其他登录方式选择
3. 用户注册页面
#### mod_search：
1. 搜索页面
2. 用户搜索历史数据
3. 搜索推荐数据
#### mod_video：
1. RecyclerView 实现防抖音短视频列表，保证全局只有一个播放器
2. ExoPlayer 播放器实现视频播放功能
3. RotateNoteView 实现旋转音乐盒
#### lib_framework：
1. Base基类相关
2. Ext拓展函数
3. Loading加载框
4. LogUtil日志打印工具类
5. Manager相关管理类
6. TipsToast吐司工具类
7. Utils相关工具类
8. 带删除按钮的EditText
#### lib_common：
1. 二次封装的Banner组件
2. 常量类
3. 实体Bean
4. 组件化通信的provider和IService
5. 通用View
#### lib_network：
1. Api接口类
2. 错误相关类
3. Flow扩展类，网络请求封装
4. Http相关拦截器
5. 相关管理类
6. BaseViewModel&BaseRepository协程网络请求封装
7. OkHttp和Retrofit封装
#### lib_stater：
1. 异步任务，延迟任务启动器
2. 任务优先级，线程池，依赖关系，是否需要等待
#### lib_banner：
1. 功能全面的Banner组件，lifecycle关联Activity/Fragment生命周期
#### lib_glide：
1. 对Glide使用的二次封装
2. 圆角，圆形，缓存，高斯模糊图片加载
#### lib_room：
1. Room数据库相关
2. 视频列表缓存

### 3. 收获

除去可以学到 **Kotlin + MVVM + Android Jetpack + 协程 + Flow + 组件化 + 模块化 + 短视频** 的知识，相信你还可以在我的项目中学到：

1.  如何使用 Charles 抓包。
2.  提供大量扩展函数，快速开发，提高效率。
3.  `ChipGroup` 和 `FlexboxLayoutManager` 等多种原生方式实现流式布局。
4.  符合阿里巴巴 Java 开发规范和阿里巴巴 Android 开发规范，并有良好的注释。
5.  `CoordinatorLayout` 和 `Toolbar` 实现首页栏目吸顶效果和轮播图电影效果。
6.  利用 `ViewOutlineProvider` 给控件添加圆角，大大减少手写 shape 圆角 xml。
7.  `ConstraintLayout` 的使用，几乎每个界面布局都采用的 `ConstraintLayout`。
8.  异步任务启动器，优雅地处理 Application 中同步初始化任务问题，有效减少  APP启动耗时。
9.  无论是模块化或者组件化，它们本质思想都是一样的，都是化整为零，化繁为简，两者的目的都是为了重用和解耦，只是叫法不一样。

## 二、Jetpack 组件进阶

> [1.浅谈 Android Jetpack - Navigation的架构设计](https://juejin.cn/post/7241184271318515773)
- Navigation的简单用法，架构设计，源码剖析
> [2.Android架构灵魂组件Lifecycle的生命周期机制详解](https://juejin.cn/post/7243413934765195323)
- Lifecycle的使用，架构设计，生命周期机制
> [3.由浅入深，ViewModel配置变更复用原理详解](https://juejin.cn/post/7245980207316189242)
- ViewModel因配置变更保存和复用机制
> [4.ViewModel进阶 | 使用SavedState实现数据复用的另一种方式](https://juejin.cn/post/7248207744087916581)
- 使用SavedState实现数据复用的另一种方式
- [5.关于LiveData全面详解](https://juejin.cn/post/7251182449400414265)
- LiveData解析，事件总线实现

## 三、Kotlin 协程学习三部曲

> [Kotlin 协程实战进阶(一、筑基篇)](https://juejin.cn/post/6987724340775108622)
- 协程的概念和原理、协程框架的基础使用、挂起函数以及挂起与恢复等
> [Kotlin 协程实战进阶(二、进阶篇)](https://juejin.cn/post/6992629783674748936)
- 协程的高级用法、Flow、Channel等
> [Kotlin 协程实战进阶(三、原理篇)](https://juejin.cn/post/7143386748783968292)
- 协程的底层原理：状态机，挂起与恢复，线程切换原理

创作不易，如果本项目对您有帮助，麻烦点个**Star**，您的**Star**将是我继续创作和写博客的动力！
项目中有APK文件，可以直接安装看效果。

欢迎在 **Issue** 中提交对本仓库的改进建议~

## 四、注意事项

本项目API均来源于[**WanAndroid**](https://www.wanandroid.com)，禁止商用。

- #### 掘金：[https://juejin.cn/user/1654096907477549](https://juejin.cn/user/1654096907477549)

感谢您的阅读~

### 致谢

**API：**  鸿洋提供的 [**WanAndroid API**](https://www.wanandroid.com/blog/show/2)

**主要使用的开源框架:**

*   [**Retrofit**](https://github.com/square/retrofit)
*   [**OkHttp**](https://github.com/square/okhttp)
*   [**Glide**](https://github.com/bumptech/glide)
*   [**ARouter**](https://github.com/alibaba/ARouter)
*   [**MMKV**](https://github.com/Tencent/MMKV)
*   [**RxPermission**](https://github.com/tbruyelle/RxPermissions)
*   [**SmartRefreshLayout**](https://github.com/scwang90/SmartRefreshLayout)
