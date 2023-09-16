package com.myandy.search.service

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.SEARCH_SERVICE_SEARCH
import com.myandy.common.service.ISearchService
import com.myandy.search.activity.SearchActivity
import com.myandy.search.manager.SearchManager

/**
 * @desc   搜索Service
 * 提供对ISearchService接口的具体实现
 */
@Route(path = SEARCH_SERVICE_SEARCH)
class SearchService : ISearchService {

    /**
     * 跳转搜索页
     * @param context
     */
    override fun toSearch(context: Context) {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }

    /**
     * 清除搜索历史缓存
     */
    override fun clearSearchHistoryCache() {
        SearchManager.clearSearchHistory()
    }

    override fun init(context: Context?) {

    }
}