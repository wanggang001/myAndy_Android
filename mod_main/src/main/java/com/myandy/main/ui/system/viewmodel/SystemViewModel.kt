package com.myandy.main.ui.system.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.myandy.common.model.SystemList
import com.myandy.framework.toast.TipsToast
import com.myandy.network.callback.IApiErrorCallback
import com.myandy.network.manager.ApiManager
import com.myandy.network.viewmodel.BaseViewModel

class SystemViewModel : BaseViewModel() {
    //错误无数据回调
    val errorListLiveData: MutableLiveData<String?> = MutableLiveData()

    /**
     * 获取体系列表
     */
    fun getSystemList(): LiveData<MutableList<SystemList>> {
        return liveData {
            val data = safeApiCallWithResult(errorCall = object : IApiErrorCallback {
                override fun onError(code: Int?, error: String?) {
                    TipsToast.showTips(error)
                    errorListLiveData.value = error
                }
            }) {
                ApiManager.api.getSystemList()
            }
            data?.let {
                emit(it)
            } ?: kotlin.run {
                errorListLiveData.value = ""
            }
        }
    }
}