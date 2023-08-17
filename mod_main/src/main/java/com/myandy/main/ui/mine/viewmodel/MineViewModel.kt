package com.myandy.main.ui.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myandy.common.model.ArticleInfo
import com.myandy.framework.toast.TipsToast
import com.myandy.network.callback.IApiErrorCallback
import com.myandy.network.error.ERROR
import com.myandy.network.manager.ApiManager
import com.myandy.network.viewmodel.BaseViewModel

class MineViewModel : BaseViewModel() {
    val recommendLiveData: MutableLiveData<MutableList<ArticleInfo>?> = MutableLiveData()
    val collectLiveData: MutableLiveData<Int?> = MutableLiveData()

    /**
     * 首页推荐列表
     * @param count 页码
     * @param pageSize 每页数量
     */
    fun getRecommendList(count: Int, pageSize: Int = 20): LiveData<MutableList<ArticleInfo>?> {
        launchUIWithResult(responseBlock = {
            ApiManager.api.getHomeList(count, pageSize)
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                TipsToast.showTips(error)
                recommendLiveData.value = null
            }
        }, successBlock = {
            if (it == null || it.datas.isNullOrEmpty()) {
                recommendLiveData.value = null
            } else {
                recommendLiveData.value = it.datas
            }
        })
        return recommendLiveData
    }

    /**
     * 收藏 or 取消收藏站内文章
     * @param id  文章id
     * @param isCollect 是否收藏
     */
    fun collectArticle(id: Int, isCollect: Boolean): LiveData<Int?> {
        launchUIWithResult(responseBlock = {
            if (!isCollect) {
                //收藏站内文章
                ApiManager.api.collectArticle(id)
            } else {
                //取消收藏站内文章
                ApiManager.api.cancelCollectArticle(id)
            }
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
                collectLiveData.value = null
            }

            override fun onLoginFail(code: Int?, error: String?) {
                super.onLoginFail(code, error)
                collectLiveData.value = ERROR.UNLOGIN.code
            }
        }) {
            collectLiveData.value = if (isCollect) 0 else 1
        }
        return collectLiveData
    }
}