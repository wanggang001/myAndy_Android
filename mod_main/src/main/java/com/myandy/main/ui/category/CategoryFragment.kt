package com.myandy.main.ui.category

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.myandy.framework.adapter.ViewPage2FragmentAdapter
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.toJson
import com.myandy.main.databinding.FragmentCategoryBinding
import com.myandy.main.ui.category.adapter.CategoryTabAdapter
import com.myandy.main.ui.category.viewmodel.CategoryViewModel


class CategoryFragment : BaseMvvmFragment<FragmentCategoryBinding, CategoryViewModel>() {
    //当前选中的position
    private var mCurrentSelectPosition = 0
    private lateinit var mTabAdapter: CategoryTabAdapter
    private var fragments = SparseArray<Fragment>()
    private var mViewPagerAdapter: ViewPage2FragmentAdapter? = null

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initTabRecyclerView()
        initViewPager2()
    }

    private fun initTabRecyclerView() {
        mTabAdapter = CategoryTabAdapter()
        mBinding?.tabRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mTabAdapter
        }
    }

    private fun initViewPager2() {
        activity?.let {
            mViewPagerAdapter = ViewPage2FragmentAdapter(childFragmentManager, lifecycle, fragments)
        }
        mBinding?.viewPager2?.apply {
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            orientation = ViewPager2.ORIENTATION_VERTICAL
            registerOnPageChangeCallback(viewPager2Callback)
            adapter = mViewPagerAdapter
        }
        mTabAdapter.onItemClickListener = { _: View, position: Int ->
            updateTabItem(position)
            mBinding?.viewPager2?.setCurrentItem(position, false)
        }
    }

    override fun initData() {
        showLoading()
        mViewModel.getCategoryData()
        mViewModel.categoryItemLiveData.observe(this) {
            dismissLoading()
            it?.let {
                mBinding?.viewEmptyData?.gone()
                //默认第一条选中
                it.firstOrNull()?.isSelected = true
                mTabAdapter.setData(it)
                it.forEachIndexed { index, item ->
                    val fragment =
                        CategorySecondFragment.newInstance(item.articles?.toJson(true) ?: "")
                    fragments.append(index, fragment)
                }
                mViewPagerAdapter?.notifyItemRangeChanged(0, it.size)
            }
        }
    }

    /**
     * 更新Tab状态
     * @param position 选择的item
     */
    private fun updateTabItem(position: Int) {
        mTabAdapter.setCurrentPosition(position)

        if (mCurrentSelectPosition != position) {
            //更新上一条item
            val selectedItem = mTabAdapter.getItem(mCurrentSelectPosition)
            selectedItem?.let { it.isSelected = false }
            //更新当前item
            val newItem = mTabAdapter.getItem(position)
            newItem?.let { it.isSelected = true }

            mCurrentSelectPosition = position
            mTabAdapter.notifyDataSetChanged()
            mBinding?.tabRecyclerView?.smoothScrollToPosition(position)
        }
    }

    /**
     * VIewPager2选择回调
     */
    private val viewPager2Callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateTabItem(position)
        }
    }

    override fun onDestroyView() {
        mBinding?.viewPager2?.unregisterOnPageChangeCallback(viewPager2Callback)
        super.onDestroyView()
    }
}