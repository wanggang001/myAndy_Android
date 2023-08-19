package com.myandy.main.ui.system.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.myandy.common.model.SystemSecondList
import com.myandy.main.databinding.LayoutSystemSecondItemBinding
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter

/**
 * @desc   体系adapter
 */
class SystemSecondAdapter :
    BaseRecyclerViewAdapter<SystemSecondList, LayoutSystemSecondItemBinding>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutSystemSecondItemBinding {
        return LayoutSystemSecondItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutSystemSecondItemBinding>,
        item: SystemSecondList?,
        position: Int
    ) {
        if (item == null) return
        holder.binding.apply {
            tvName.text = item.name
        }
    }

}