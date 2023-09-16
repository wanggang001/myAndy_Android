package com.myandy.main.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.myandy.common.constant.DEMO_ACTIVITY_LIFECYCLE
import com.myandy.common.constant.DEMO_ACTIVITY_LIVEDATA
import com.myandy.common.constant.DEMO_ACTIVITY_NAVIGATION
import com.myandy.common.constant.DEMO_ACTIVITY_VIEWMODEL
import com.myandy.common.constant.USER_ACTIVITY_COLLECTION
import com.myandy.common.constant.USER_ACTIVITY_INFO
import com.myandy.common.constant.USER_ACTIVITY_SETTING
import com.myandy.common.model.User
import com.myandy.common.provider.LoginServiceProvider
import com.myandy.common.provider.MainServiceProvider
import com.myandy.common.provider.UserServiceProvider
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.decoration.NormalItemDecoration
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.framework.log.LogUtil
import com.myandy.framework.toast.TipsToast
import com.myandy.framework.utils.dpToPx
import com.myandy.framework.utils.getStringFromResource
import com.myandy.glide.loadFile
import com.myandy.main.R
import com.myandy.main.databinding.FragmentMineBinding
import com.myandy.main.databinding.FragmentMineHeadBinding
import com.myandy.main.ui.mine.viewmodel.MineViewModel
import com.myandy.main.ui.system.adapter.ArticleAdapter
import com.myandy.network.error.ERROR
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import java.io.File

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

    override fun initData() {
        val user = UserServiceProvider.getUserInfo()
        setUserInfo(user)
        UserServiceProvider.getUserLiveData().observe(this) {
            setUserInfo(it)
        }
    }

    override fun onFragmentVisible(isVisibleToUser: Boolean) {
        LogUtil.e("isVisibleToUser:$isVisibleToUser")
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
        mAdapter.onItemClickListener = { _: View, position: Int ->
            val item = mAdapter.getItem(position)
            if (item != null && !item.link.isNullOrEmpty()) {
                MainServiceProvider.toArticleDetail(
                    context = requireContext(),
                    url = item.link!!,
                    title = item.title ?: ""
                )
            }
        }
        mAdapter.onItemCollectListener = { _: View, position: Int ->
            if (LoginServiceProvider.isLogin()) {
                setCollectView(position)
            } else {
                LoginServiceProvider.login(requireContext())
            }
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
            ivHead.onClick {
                if (UserServiceProvider.isLogin()) {
                    ARouter.getInstance().build(USER_ACTIVITY_INFO).navigation()
                } else {
                    LoginServiceProvider.login(requireContext())
                }
            }
            ivSetting.onClick {
                ARouter.getInstance().build(USER_ACTIVITY_SETTING).navigation()
            }
            tvVideo.onClick {

            }
            tvWorkTitle.onClick {

            }
            tvLikeTitle.onClick {
                if (UserServiceProvider.isLogin()) {
                    ARouter.getInstance().build(USER_ACTIVITY_COLLECTION).navigation()
                } else {
                    LoginServiceProvider.login(requireContext())
                }
            }
//            tvNavigation.onClick {
//                ARouter.getInstance().build(DEMO_ACTIVITY_NAVIGATION).navigation()
//            }
//            tvLifeCycle.onClick {
//                ARouter.getInstance().build(DEMO_ACTIVITY_LIFECYCLE).navigation()
//            }
            tvDataBinging.onClick {

            }
//            tvLivedata.onClick {
//                ARouter.getInstance().build(DEMO_ACTIVITY_LIVEDATA).navigation()
//            }
//            tvViewModel.onClick {
//                ARouter.getInstance().build(DEMO_ACTIVITY_VIEWMODEL).navigation()
//            }
            tvPaging.onClick {

            }
            tvRoom.onClick {

            }
            tvHilt.onClick {

            }
        }
    }

    /**
     * 设置用户信息
     */
    private fun setUserInfo(user: User?) {
        LogUtil.e("userdata:$user", tag = "smy")
        if (UserServiceProvider.isLogin()) {
            user?.let {
                mHeadBinding.ivHead.loadFile(File(it.icon ?: ""))
                if (!it.nickname.isNullOrEmpty()) {
                    mHeadBinding.tvName.text = it.nickname
                } else {
                    mHeadBinding.tvName.text = it.username
                }
                mHeadBinding.tvDesc.text = it.signature
            } ?: kotlin.run {

            }
        } else {
            mHeadBinding.tvName.text = getStringFromResource(R.string.mine_not_login)
            mHeadBinding.tvDesc.text =
                getStringFromResource(com.myandy.common.R.string.login_know_more_android)
        }
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
                    LoginServiceProvider.login(requireContext())
                }
            }
        }
    }
}