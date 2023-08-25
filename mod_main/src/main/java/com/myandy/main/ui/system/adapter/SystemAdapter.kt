package com.myandy.main.ui.system.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.myandy.common.model.SystemList
import com.myandy.main.databinding.LayoutSystemItemBinding
import com.myandy.framework.adapter.BaseBindViewHolder
import com.myandy.framework.adapter.BaseRecyclerViewAdapter

class SystemAdapter : BaseRecyclerViewAdapter<SystemList, LayoutSystemItemBinding>() {
    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutSystemItemBinding {
        return LayoutSystemItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutSystemItemBinding>,
        item: SystemList?,
        position: Int
    ) {
        if (item == null) return
        holder.binding.apply {
            tvTitle.text = item.name

            val layoutManager = FlexboxLayoutManager(recyclerViewItem.context)
            layoutManager.flexWrap = FlexWrap.WRAP // 按正常方向换行
            layoutManager.flexDirection = FlexDirection.ROW // 水平方向
            layoutManager.justifyContent = JustifyContent.FLEX_START // 左对齐
            recyclerViewItem.layoutManager = layoutManager

            val adapter = SystemSecondAdapter()
            recyclerViewItem.adapter = adapter
            adapter.setData(item.children)
        }
    }
}