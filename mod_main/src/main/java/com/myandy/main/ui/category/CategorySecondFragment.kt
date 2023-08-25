package com.myandy.main.ui.category

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.myandy.common.constant.KEY_LIST
import com.myandy.common.model.CategorySecondItem
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.ext.dividerGridSpace
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.toBeanOrNull
import com.myandy.framework.ext.visible
import com.myandy.main.databinding.FragmentCategorySecondBinding
import com.myandy.main.ui.category.adapter.CategorySecondItemAdapter
import com.myandy.main.ui.category.viewmodel.CategoryViewModel

class CategorySecondFragment :
    BaseMvvmFragment<FragmentCategorySecondBinding, CategoryViewModel>() {
    private lateinit var mAdapter: CategorySecondItemAdapter

    companion object {
        fun newInstance(jsonStr: String): CategorySecondFragment {
            val fragment = CategorySecondFragment()
            val bundle = Bundle()
            bundle.putString(KEY_LIST, jsonStr)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mAdapter = CategorySecondItemAdapter()
        mBinding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mAdapter
            dividerGridSpace(2, 8.0f, true)
        }
    }

    override fun initData() {
        val listJson = arguments?.getString(KEY_LIST, "")
        val list = listJson?.toBeanOrNull<MutableList<CategorySecondItem>>()
        mAdapter.setData(list)
        mAdapter.setData(list)
        if (list.isNullOrEmpty()) {
            mBinding?.viewEmptyData?.visible()
        } else {
            mBinding?.viewEmptyData?.gone()
        }
    }
}