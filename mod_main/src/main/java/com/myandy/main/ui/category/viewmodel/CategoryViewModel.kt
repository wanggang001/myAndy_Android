package com.myandy.main.ui.category.viewmodel

import androidx.lifecycle.MutableLiveData
import com.myandy.common.model.CategoryItem
import com.myandy.framework.toast.TipsToast
import com.myandy.network.callback.IApiErrorCallback
import com.myandy.network.manager.ApiManager
import com.myandy.network.viewmodel.BaseViewModel

class CategoryViewModel : BaseViewModel() {

    val categoryItemLiveData = MutableLiveData<MutableList<CategoryItem>?>()

    /**
     * 获取分类信息
     * 不依赖repository,错误回调实现IApiErrorCallback
     */
    fun getCategoryData() {
        launchUIWithResult(responseBlock = {
            ApiManager.api.getCategoryData()
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
                TipsToast.showTips(error)
                categoryItemLiveData.value = null
            }
        }) {
            categoryItemLiveData.value = it
        }
    }
}