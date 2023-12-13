package com.bestway.broncoforreddit.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hot_posts")
data class HotPost(
    @PrimaryKey(autoGenerate = false)
    val postId: String,
    val subName: String? = null,
    val title: String? = null,
    val description: String? = null,
    val upVotes: Int? = null,
    val comments: Int? = null,
    val imageUrl: String? = null,
    val postUrl: String? = null,
    val videoUrl: String? = null,
    val gifUrl: String? = null,
    val author: String = "",
    val before: String? = null,
    val after: String? = null
)
