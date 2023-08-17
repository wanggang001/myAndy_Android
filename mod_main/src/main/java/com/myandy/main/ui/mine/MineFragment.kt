package com.myandy.main.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.myandy.common.constant.USER_ACTIVITY_SETTING
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.decoration.NormalItemDecoration
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.framework.utils.dpToPx
import com.myandy.framework.utils.getStringFromResource
import com.myandy.main.R
import com.myandy.main.databinding.FragmentMineBinding
import com.myandy.main.databinding.FragmentMineHeadBinding
import com.myandy.main.ui.mine.viewmodel.MineViewModel
import com.myandy.main.ui.system.adapter.ArticleAdapter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class MineFragment : BaseMvvmFragment<FragmentMineBinding, MineViewModel>(), OnRefreshListener,
    OnLoadMoreListener {

    private var mPage = 0
    private lateinit var mHeadBinding: FragmentMineHeadBinding
    private lateinit var mAdapter: ArticleAdapter
    override fun initView(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initHeadView()
        initListener()
    }

    private fun initRecyclerView() {
        mBinding?.refreshLayout?.apply {
            autoRefresh()
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@MineFragment)
            setOnLoadMoreListener(this@MineFragment)
            autoRefresh()
        }
        mAdapter = ArticleAdapter()
        val dp12 = dpToPx(12)
        mBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(NormalItemDecoration().apply {
                setBounds(left = dp12, top = dp12, right = dp12, bottom = dp12)
                setLastBottom(true)
                setFirstHeadMargin(true)
            })
            adapter = mAdapter
        }
    }

    private fun initHeadView() {
        mHeadBinding = FragmentMineHeadBinding.inflate(LayoutInflater.from(requireContext()))
        mHeadBinding?.tvName?.text = getStringFromResource(R.string.mine_not_login)
        mAdapter.addHeadView(mHeadBinding.root)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 0
        getRecommendList()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        getRecommendList()
    }

    /**
     * 获取推荐列表数据
     */
    private fun getRecommendList() {
        mViewModel.getRecommendList(count = mPage).observe(this) {
            if (mPage == 0) {
                mAdapter.setData(it)
                if (it.isNullOrEmpty()) {
                    mHeadBinding.tvRecommendTitle.gone()
                } else {
                    mHeadBinding.tvRecommendTitle.visible()
                }
                mBinding?.refreshLayout?.finishRefresh()
            } else {
                mAdapter.addAll(it)
                mBinding?.refreshLayout?.finishLoadMore()
            }
        }
    }

    private fun initListener() {
        mHeadBinding?.apply {
            ivSetting.onClick {
                ARouter.getInstance().build(USER_ACTIVITY_SETTING).navigation()
            }
        }
    }
}