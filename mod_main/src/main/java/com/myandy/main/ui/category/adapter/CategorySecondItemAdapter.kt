package com.myandy.main.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.myandy.common.model.CategorySecondItem
import com.myandy.framework.adapter.BaseBindViewHolder
import com.myandy.framework.adapter.BaseRecyclerViewAdapter
import com.myandy.framework.utils.ViewUtils
import com.myandy.framework.utils.dpToPx
import com.myandy.main.databinding.LayoutCategorySecondItemBinding

class CategorySecondItemAdapter :
    BaseRecyclerViewAdapter<CategorySecondItem, LayoutCategorySecondItemBinding>() {
    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutCategorySecondItemBinding {
        return LayoutCategorySecondItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutCategorySecondItemBinding>,
        item: CategorySecondItem?,
        position: Int
    ) {
        holder.binding?.apply {
            tvTitle.text = item?.title
            ViewUtils.setClipViewCornerRadius(tvTitle, dpToPx(8))
        }
    }


}