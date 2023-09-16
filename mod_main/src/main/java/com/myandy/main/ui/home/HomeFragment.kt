package com.myandy.main.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.myandy.common.model.ProjectTabItem
import com.myandy.common.provider.SearchServiceProvider
import com.myandy.framework.adapter.ViewPage2FragmentAdapter
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.main.R
import com.myandy.main.databinding.FragmentHomeBinding
import com.myandy.main.ui.home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener


class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel>(), OnRefreshListener {

    private val mArrayTabFragments = SparseArray<Fragment>()

    private var mTabLayoutMediator: TabLayoutMediator? = null
    private var mFragmentAdapter: ViewPage2FragmentAdapter? = null
    private var mProjectTabs: MutableList<ProjectTabItem> = mutableListOf()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mBinding?.refreshLayout?.apply {
            autoRefresh()
            setEnableRefresh(true)
            setEnableLoadMore(false)
            setOnRefreshListener(this@HomeFragment)
        }
        mBinding?.ivSearch?.onClick {
            SearchServiceProvider.toSearch(requireContext())
        }
        initTab()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        refresh()
    }

    private fun refresh() {
        mViewModel.getBannerList().observe(this) { banners ->
            banners?.let {
                mBinding?.bannerHome?.visible()
                mBinding?.bannerHome?.setData(it)
            } ?: kotlin.run {
                mBinding?.bannerHome?.gone()
            }
            mBinding?.refreshLayout?.finishRefresh()
        }

        mViewModel.getProjectTab().observe(this) { tabs ->
            tabs?.forEachIndexed { index, item ->
                mProjectTabs.add(item)
                mArrayTabFragments.append(index, HomeTabFragment.newInstance(tabs[index].id))
            }
            mFragmentAdapter?.setData(mArrayTabFragments)
            mFragmentAdapter?.notifyItemRangeChanged(0, mArrayTabFragments.size())

            // 解决 TabLayout 刷新数据后滚动到错误位置
            mBinding?.tabHome?.let {
                it.post { it.getTabAt(0)?.select() }
            }
        }
    }

    private fun initTab() {
        activity?.let {
            mFragmentAdapter =
                ViewPage2FragmentAdapter(childFragmentManager, lifecycle, mArrayTabFragments)
        }
        mBinding?.let {
            it.viewPager.adapter = mFragmentAdapter
            //可左右滑动
            it.viewPager.isUserInputEnabled = true
            //禁用预加载
//            (it.viewPager.getChildAt(0) as RecyclerView).layoutManager?.isItemPrefetchEnabled = false
            //需要注意是FragmentStateAdapter不会一直保持Fragment实例，在被destroy后，需要做好Fragment重建后回复数据的准备，这点可以结合ViewModel来进行配合使用。
            it.viewPager.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT_DEFAULT

            mTabLayoutMediator =
                TabLayoutMediator(it.tabHome, it.viewPager) { tab: TabLayout.Tab, position: Int ->
                    tab.text = mProjectTabs[position].name
                }

            //tabLayout和viewPager2关联起来
            mTabLayoutMediator?.attach()

            //增加tab选择监听
            it.tabHome.addOnTabSelectedListener(tabSelectedCall)
            //设置第一个tab效果
            val tabFirst = it.tabHome.getTabAt(0)
            setTabTextSize(tabFirst)
        }
    }

    /**
     * tab选择回调
     */
    private val tabSelectedCall = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            setTabTextSize(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            //非选中效果在xml中设置
            tab?.customView = null
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    /**
     * 设置tab大小加粗效果
     */
    private fun setTabTextSize(tabFirst: TabLayout.Tab?) {
        TextView(requireContext()).apply {
            typeface = Typeface.DEFAULT_BOLD
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }.also {
            it.text = tabFirst?.text
            tabFirst?.customView = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator?.detach()
    }
}