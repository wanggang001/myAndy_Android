package com.myandy.main.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.myandy.common.model.Banner
import com.myandy.common.model.ProjectSubInfo
import com.myandy.common.model.ProjectTabItem
import com.myandy.framework.toast.TipsToast
import com.myandy.main.repository.HomeRepository
import com.myandy.network.flow.requestFlow
import com.myandy.network.manager.ApiManager
import com.myandy.network.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    val projectItemLiveData = MutableLiveData<MutableList<ProjectSubInfo>?>()
    private val bannerLiveDate = MutableLiveData<MutableList<Banner>?>()
    private val homeRepository by lazy { HomeRepository() }

    /**
     * 首页banner
     */
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

    /**
     * 首页Project tab
     */
    fun getProjectTab(): LiveData<MutableList<ProjectTabItem>?> {
        return liveData {
            val response = safeApiCall(errorBlock = { code, errorMsg ->
                TipsToast.showTips(errorMsg)
            }) {
                homeRepository.getProjectTab()
            }
            emit(response)
        }
    }

    /**
     * 获取项目列表数据
     * @param page
     * @param cid
     */
    fun getProjectList(page: Int, cid: Int): LiveData<MutableList<ProjectSubInfo>?> {
        launchUI(errorBlock = { code, errorMsg ->
            TipsToast.showTips(errorMsg)
            projectItemLiveData.value = null
        }) {
            val data = homeRepository.getProjectList(page, cid)
            projectItemLiveData.value = data?.datas
        }
        return projectItemLiveData
    }
}