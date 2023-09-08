package com.myandy.main.repository

import com.myandy.common.model.ArticleList
import com.myandy.common.model.Banner
import com.myandy.common.model.ProjectSubList
import com.myandy.common.model.ProjectTabItem
import com.myandy.network.manager.ApiManager
import com.myandy.network.repository.BaseRepository

/**
 * @desc   首页请求仓库
 */
class HomeRepository : BaseRepository() {
    /**
     * 首页Banner
     */
    suspend fun getHomeBanner(): MutableList<Banner>? {
        return requestResponse {
            ApiManager.api.getHomeBanner()
        }
    }

    /**
     * 首页列表
     * @param page 页码
     * @param pageSize 每页数量
     */
    suspend fun getHomeInfoList(page: Int): ArticleList? {
        return requestResponse {
            ApiManager.api.getHomeList(page, 20)
        }
    }

    /**
     * 项目tab
     */
    suspend fun getProjectTab(): MutableList<ProjectTabItem>? {
        return requestResponse {
            ApiManager.api.getProjectTab()
        }
    }

    /**
     * 项目列表
     * @param page
     * @param cid
     */
    suspend fun getProjectList(page: Int, cid: Int): ProjectSubList? {
        return requestResponse {
            ApiManager.api.getProjectList(page, cid)
        }
    }

}