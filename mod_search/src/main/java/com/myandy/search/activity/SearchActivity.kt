package com.myandy.search.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.SEARCH_ACTIVITY_SEARCH
import com.myandy.common.dialog.MessageDialog
import com.myandy.common.provider.LoginServiceProvider
import com.myandy.common.provider.MainServiceProvider
import com.myandy.framework.base.BaseMvvmActivity
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.framework.toast.TipsToast
import com.myandy.framework.utils.ViewUtils
import com.myandy.framework.utils.dpToPx
import com.myandy.framework.utils.getColorFromResource
import com.myandy.framework.utils.getStringFromResource
import com.myandy.search.R
import com.myandy.search.SearchResultAdapter
import com.myandy.search.databinding.ActivitySearchBinding
import com.myandy.search.manager.SearchManager
import com.myandy.search.viewmodel.SearchViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener

/**
 * @desc   搜索Activity
 */
@Route(path = SEARCH_ACTIVITY_SEARCH)
class SearchActivity : BaseMvvmActivity<ActivitySearchBinding, SearchViewModel>(), OnLoadMoreListener {
    private var page = 0
    private lateinit var mAdapter: SearchResultAdapter

    /**
     * 搜索历史item点击
     */
    private val clickCallBack = { keyWord: String ->
        mBinding.etSearch.setText(keyWord)
        getSearchResult()
    }

    override fun initView(savedInstanceState: Bundle?) {
        initRecyclerView()
        initListener()
        window.statusBarColor = getColorFromResource(com.myandy.common.R.color.color_f0f2f4)
        ViewUtils.setClipViewCornerRadius(mBinding.etSearch, dpToPx(6))
        ViewUtils.setClipViewCornerRadius(mBinding.tvSearch, dpToPx(4))
        ViewUtils.setClipViewCornerTopRadius(mBinding.clSearchResult, dpToPx(14))
        ViewUtils.setClipViewCornerTopRadius(mBinding.viewSearchHistory, dpToPx(14))
    }

    private fun initListener() {
        mBinding.searchBack.onClick {
            finish()
        }
        mBinding.tvSearch.onClick {
            page = 0
            getSearchResult()
        }
//        mBinding.etSearch.textChangeFlow()
//                .filter { it.isNotEmpty() }
//                .debounce(300)
//                //.flatMapLatest { searchFlow(it.toString()) }
//                .flowOn(Dispatchers.IO)
//                .onEach {
//                    LogUtil.e("结果：$it")
//                }
//                .launchIn(lifecycleScope)
        mBinding.etSearch.addTextChangedListener {
            val content = it.toString()
            if (content.isEmpty()) {
                mBinding.clSearchResult.gone()
            }
        }
        mBinding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == (EditorInfo.IME_ACTION_SEARCH)) {
                getSearchResult()
            }
            return@setOnEditorActionListener false
        }
        mBinding.viewSearchHistory.setOnHistoryClearListener {
            clearHistoryCache()
        }
        mBinding.viewSearchHistory.setOnCheckChangeListener(clickCallBack)
        mBinding.viewSearchRecommend.setOnCheckChangeListener(clickCallBack)
    }

    /**
     * 清楚搜索历史
     */
    private fun clearHistoryCache() {
        MessageDialog.Builder(this).setTitle(getStringFromResource(com.myandy.common.R.string.dialog_tips_title))
            .setMessage(getStringFromResource(R.string.search_clear_history))
            .setConfirm(getStringFromResource(com.myandy.common.R.string.default_confirm))
            .setConfirmTxtColor(getColorFromResource(com.myandy.common.R.color.color_0165b8))
            .setCancel(getString(com.myandy.common.R.string.default_cancel))
            .setonCancelListener {
                it?.dismiss()
            }
            .setonConfirmListener {
                SearchManager.clearSearchHistory()
                it?.dismiss()
            }.create().show()
    }

    private fun initRecyclerView() {
        mBinding.refreshLayout.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener(this@SearchActivity)
            autoRefresh()
        }
        mAdapter = SearchResultAdapter()
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = mAdapter
        }
        mAdapter.onItemClickListener = { view: View, position: Int ->
            val item = mAdapter.getItem(position)
            if (item != null && !item.link.isNullOrEmpty()) {
                MainServiceProvider.toArticleDetail(
                    context = this,
                    url = item.link!!,
                    title = item.title ?: ""
                )
            }
        }
        mAdapter.onItemCollectListener = { _: View, position: Int ->
            if (LoginServiceProvider.isLogin()) {
                collectArticle(position)
            } else {
                LoginServiceProvider.login(this)
            }
        }
    }

    override fun initData() {
        mViewModel.getHotSearchData().observe(this) { hotList ->
            val list = hotList?.map { it.name ?: "" }?.toMutableList()
            mBinding.viewSearchRecommend.setHistoryData(list)
        }
        mBinding.viewSearchRecommend.getDeleteImageView().gone()

        setSearchHistory()

        mViewModel.searchResultLiveData.observe(this) {
            if (page == 0) {
                mAdapter.setData(it)
                if (it.isNullOrEmpty()) {
                    //空视图
                    mBinding.viewEmptyData.visible()
                } else {
                    mBinding.viewEmptyData.gone()
                }
            } else {
                mAdapter.addAll(it)
                mBinding.refreshLayout.finishLoadMore()
            }
        }
    }

    /**
     * 设置搜索历史
     */
    private fun setSearchHistory() {
        val historyList = SearchManager.getSearchHistory()?.reversed()?.toMutableList()
        mBinding.viewSearchHistory.setHistoryData(historyList)
    }

    /**
     * 搜索结果
     */
    private fun getSearchResult() {
        val keyWord = mBinding.etSearch.text.toString()
        mViewModel.searchResult(page, keyWord)
        if (page == 0 && keyWord.isNotEmpty()) {
            SearchManager.addSearchHistory(keyWord)
            setSearchHistory()
            mBinding.clSearchResult.visible()
            mBinding.viewEmptyData.visible()
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        getSearchResult()
    }

    /**
     * 收藏 or 取消收藏
     */
    private fun collectArticle(position: Int) {
        val item = mAdapter.getItem(position)

        if (item != null) {
            showLoading()
            val collect = item.collect ?: false
            mViewModel.collectArticle(item.id, collect).observe(this) {
                dismissLoading()
                it?.let {
                    val tipsRes = if (collect) com.myandy.common.R.string.collect_cancel else com.myandy.common.R.string.collect_success
                    TipsToast.showSuccessTips(tipsRes)
                    item.collect = !collect
                    mAdapter.updateItem(position, item)
                }
            }
        }
    }
}