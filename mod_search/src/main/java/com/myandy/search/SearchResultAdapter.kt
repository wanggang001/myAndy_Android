package com.myandy.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myandy.common.R
import com.myandy.common.model.ArticleInfo
import com.myandy.framework.adapter.BaseBindViewHolder
import com.myandy.framework.adapter.BaseRecyclerViewAdapter
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.framework.utils.getStringFromResource
import com.myandy.search.databinding.LayoutSearchResultItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @desc   文章列表Item
 */
class SearchResultAdapter : BaseRecyclerViewAdapter<ArticleInfo, LayoutSearchResultItemBinding>() {
    var onItemCollectListener: ((view: View, position: Int) -> Unit?)? = null
    private val format = SimpleDateFormat("yyyy-MM-dd:HH:mm", Locale.CHINA)

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutSearchResultItemBinding {
        return LayoutSearchResultItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutSearchResultItemBinding>,
        item: ArticleInfo?,
        position: Int
    ) {
        if (item == null) return
        val name = if (item.author.isNullOrEmpty()) item.shareUser else item.author
        val authorName = String.format(getStringFromResource(R.string.author_name), name)
        holder.binding.apply {
            tvTitle.text = item.title
            tvDesc.text = item.desc
            if (item.desc.isNullOrEmpty()) {
                tvDesc.gone()
            } else {
                tvDesc.visible()
            }
            tvTime.text = format.format(item.publishTime)
            tvFrom.text = "${item.superChapterName}/${item.chapterName}"
            tvAuthorName.text = authorName
            ivCollect.onClick {
                onItemCollectListener?.invoke(it, position)
            }
            ivCollect.isSelected = item.collect ?: false
        }
    }


}