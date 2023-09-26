package com.bestway.broncoforreddit.ui.features.home.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.data.models.ChildrenData
import com.bestway.broncoforreddit.data.models.ListingsChildren
import com.bestway.broncoforreddit.ui.features.home.components.PostWidget

class PostAdapter : ListAdapter<ListingsChildren, PostAdapter.PostViewHolder>(PostDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        return PostViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.reddit_post_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem.childrenData)
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val composeView = itemView.findViewById<ComposeView>(R.id.compose_view_reddit_post)

        fun bind(data: ChildrenData) {
            composeView.setContent {
                PostWidget(
                    subName = data.subName.orEmpty(),
                    title = data.title,
                    description = data.description,
                    upVotes = data.upVotes ?: 0,
                    comments = data.comments ?: 0,
                    imageUrl = data.imageUrl,
                    postUrl = data.postUrl,
                    videoUrl = data.secureMedia?.redditVideo?.videoUrl
                )
            }
        }
    }

    class PostDiffUtil : DiffUtil.ItemCallback<ListingsChildren>() {
        override fun areItemsTheSame(
            oldItem: ListingsChildren,
            newItem: ListingsChildren
        ): Boolean {
            return oldItem.childrenData.id == newItem.childrenData.id
        }

        override fun areContentsTheSame(
            oldItem: ListingsChildren,
            newItem: ListingsChildren
        ): Boolean {
            return oldItem.childrenData == newItem.childrenData
        }


    }

}