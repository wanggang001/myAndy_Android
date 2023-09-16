package com.myandy.user.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myandy.common.R
import com.myandy.common.model.ArticleInfo
import com.myandy.framework.adapter.BaseBindViewHolder
import com.myandy.framework.adapter.BaseRecyclerViewAdapter
import com.myandy.framework.ext.Bold
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.framework.utils.ViewUtils
import com.myandy.framework.utils.dpToPx
import com.myandy.framework.utils.getStringFromResource
import com.myandy.user.databinding.LayoutMyCollectItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @desc   文章列表Item
 */
class MyCollectListAdapter : BaseRecyclerViewAdapter<ArticleInfo, LayoutMyCollectItemBinding>() {
    var onItemCancelCollectListener: ((view: View, position: Int) -> Unit?)? = null
    private val format = SimpleDateFormat("yyyy-MM-dd:HH:mm", Locale.CHINA)

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutMyCollectItemBinding {
        return LayoutMyCollectItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutMyCollectItemBinding>,
        item: ArticleInfo?,
        position: Int
    ) {
        if (item == null) return
        val name = if (item.author.isNullOrEmpty()) item.shareUser else item.author
        val authorName = String.format(getStringFromResource(R.string.author_name), name ?: "")
        holder.binding.apply {
            tvTitle.text = item.title
            tvTitle.Bold()
            tvDesc.text = item.desc
            if (item.desc.isNullOrEmpty()) {
                tvDesc.gone()
            } else {
                tvDesc.visible()
            }
            tvTime.text = if (!item.niceDate.isNullOrEmpty()) item.niceDate else format.format(item.publishTime)
            tvFrom.text = "form:${item.chapterName}"
            tvAuthorName.text = authorName
            tvCollect.onClick {
                onItemCancelCollectListener?.invoke(it, position)
            }
            ViewUtils.setClipViewCornerRadius(tvCollect, dpToPx(4))
        }
    }


}