package com.myandy.main.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.MAIN_SERVICE_HOME
import com.myandy.common.service.IMainService
import com.myandy.main.ui.ArticleDetailActivity
import com.myandy.main.MainActivity

/**
 * @desc   主页服务
 * 提供对IMainService接口的具体实现
 */
@Route(path = MAIN_SERVICE_HOME)
class MainService : IMainService {
    /**
     * 跳转主页
     * @param context
     * @param index tab位置
     */
    override fun toMain(context: Context, index: Int) {
        MainActivity.start(context, index)
    }

    /**
     * 跳转主页
     * @param context
     * @param url
     * @param title 标题
     */
    override fun toArticleDetail(context: Context, url: String, title: String) {
        ArticleDetailActivity.start(context, url, title)
    }

    override fun init(context: Context?) {
    }
}