package com.myandy.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.myandy.common.model.ProjectSubInfo
import com.myandy.framework.adapter.BaseBindViewHolder
import com.myandy.framework.adapter.BaseRecyclerViewAdapter
import com.myandy.framework.ext.onClick
import com.myandy.framework.utils.ViewUtils
import com.myandy.framework.utils.dpToPx
import com.myandy.glide.setUrl
import com.myandy.main.ui.ImagePreviewActivity
import com.myandy.main.databinding.LayoutHomeTabItemBinding

class HomeTabItemAdapter : BaseRecyclerViewAdapter<ProjectSubInfo, LayoutHomeTabItemBinding>() {
    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutHomeTabItemBinding {
        return LayoutHomeTabItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutHomeTabItemBinding>,
        item: ProjectSubInfo?,
        position: Int
    ) {
        if (item == null) return
        holder.binding.apply {
            ivMainIcon.setUrl(item.envelopePic)
            tvTitle.text = item.title
            tvSubTitle.text = item.desc
            tvAuthorName.text = item.author
            tvTime.text = item.niceDate
            ivMainIcon.onClick {
                ImagePreviewActivity.start(it.context, item.envelopePic)
            }
            ViewUtils.setClipViewCornerRadius(holder.itemView, dpToPx(8))
        }
    }


}