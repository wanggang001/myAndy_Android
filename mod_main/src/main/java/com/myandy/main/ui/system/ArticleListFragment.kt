package com.myandy.main.ui.system

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.myandy.common.constant.KEY_ID
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.decoration.NormalItemDecoration
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.visible
import com.myandy.framework.toast.TipsToast
import com.myandy.framework.utils.dpToPx
import com.myandy.main.databinding.FragmentArticleListBinding
import com.myandy.main.ui.system.adapter.ArticleAdapter
import com.myandy.main.ui.system.viewmodel.ArticleListViewModel
import com.myandy.network.error.ERROR
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class ArticleListFragment : BaseMvvmFragment<FragmentArticleListBinding, ArticleListViewModel>(),
    OnRefreshListener, OnLoadMoreListener {
    private var page = 0
    private var cId = 0
    private lateinit var mAdapter: ArticleAdapter

    companion object {
        fun newInstance(id: Int): ArticleListFragment {
            val args = Bundle()
            args.putInt(KEY_ID, id)
            val fragment = ArticleListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mBinding?.refreshLayout?.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@ArticleListFragment)
            setOnLoadMoreListener(this@ArticleListFragment)
            autoRefresh()
        }
        mAdapter = ArticleAdapter()
        val dp12 = dpToPx(12)
        mBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            addItemDecoration(NormalItemDecoration().apply {
                setBounds(left = dp12, top = dp12, right = dp12, bottom = dp12)
                setLastBottom(true)
            })
        }
        mAdapter.onItemClickListener = { _: View, position: Int ->
            val item = mAdapter.getItem(position)
            if (item != null && !item.link.isNullOrEmpty()) {
//                MainServiceProvider.toArticleDetail(
//                    context = requireContext(),
//                    url = item.link!!,
//                    title = item.title ?: ""
//                )
            }
        }
        mAdapter.onItemCollectListener = { _: View, position: Int ->
//            if (LoginServiceProvider.isLogin()) {
//                setCollectView(position)
//            } else {
//                LoginServiceProvider.login(requireContext())
//            }
        }
    }

    override fun initData() {
        cId = arguments?.getInt(KEY_ID, 0) ?: 0
        mViewModel.articleListLiveData.observe(this) {
            if (page == 0) {
                mAdapter.setData(it)
                if (it.isNullOrEmpty()) {
                    //空视图
                    mBinding?.viewEmptyData?.visible()
                } else {
                    mBinding?.viewEmptyData?.gone()
                }
                mBinding?.refreshLayout?.finishRefresh()
            } else {
                mAdapter.addAll(it)
                mBinding?.refreshLayout?.finishLoadMore()
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        getArticleList()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        getArticleList()
    }

    /**
     * 获取文章列表数据
     */
    private fun getArticleList() {
        mViewModel.getArticleList(page, cId)
    }

    /**
     * 收藏和取消收藏
     * @param position
     */
    private fun setCollectView(position: Int) {
        val data = mAdapter.getItem(position)
        data?.let { item ->
            showLoading()
            val collect = item.collect ?: false
            mViewModel.collectArticle(item.id, collect).observe(this) {
                dismissLoading()
                it?.let {
                    val tipsRes =
                        if (collect) com.myandy.common.R.string.collect_cancel else com.myandy.common.R.string.collect_success
                    TipsToast.showSuccessTips(tipsRes)
                    item.collect = !collect
                    mAdapter.updateItem(position, item)
                }

                if (it == ERROR.UNLOGIN.code) {
//                    LoginServiceProvider.login(requireContext())
                }
            }
        }
    }
}