package com.myandy.main.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myandy.common.model.Banner
import com.myandy.framework.toast.TipsToast
import com.myandy.network.flow.requestFlow
import com.myandy.network.manager.ApiManager
import com.myandy.network.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val bannerLiveDate = MutableLiveData<MutableList<Banner>?>()

    fun getBannerList(): LiveData<MutableList<Banner>?> {
        viewModelScope.launch {
            val data = requestFlow(requestCall = { ApiManager.api.getHomeBanner() },
                errorBlock = { code, error ->
                    TipsToast.showTips(error)
                    bannerLiveDate.value = null
                })
            bannerLiveDate.value = data
        }
        return bannerLiveDate
    }
}