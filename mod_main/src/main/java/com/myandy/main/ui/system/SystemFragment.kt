package com.myandy.main.ui.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myandy.framework.base.BaseMvvmFragment
import com.myandy.framework.decoration.NormalItemDecoration
import com.myandy.framework.ext.visible
import com.myandy.framework.utils.dpToPx
import com.myandy.main.R
import com.myandy.main.databinding.FragmentSystemBinding
import com.myandy.main.ui.system.adapter.SystemAdapter
import com.myandy.main.ui.system.viewmodel.SystemViewModel


class SystemFragment : BaseMvvmFragment<FragmentSystemBinding, SystemViewModel>() {
    lateinit var mAdapter: SystemAdapter
    override fun initView(view: View, savedInstanceState: Bundle?) {
        mAdapter = SystemAdapter()
        mBinding?.recyclerView?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
            addItemDecoration(NormalItemDecoration().apply {
                setBounds(
                    left = dpToPx(8),
                    top = dpToPx(10),
                    right = dpToPx(8),
                    bottom = dpToPx(10)
                )
                setLastBottom(true)
            })
        }
    }

    override fun initData() {
        showLoading()
        mViewModel.getSystemList().observe(this) {
            mAdapter.setData(it)
            dismissLoading()
        }
        mViewModel.errorListLiveData.observe(this) {
            //空数据视图
            mBinding?.viewEmptyData?.visible()
        }
    }
}