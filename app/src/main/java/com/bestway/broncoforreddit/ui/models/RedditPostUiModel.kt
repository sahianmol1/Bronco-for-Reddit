package com.bestway.broncoforreddit.ui.models

import javax.annotation.concurrent.Immutable

@Immutable
data class RedditPostUiModel(
    val subName: String = "",
    val title: String? = null,
    val description: String? = null,
    val upVotes: Int = 0,
    val comments: Int = 0,
    val imageUrl: String? = null,
    val postUrl: String? = null,
    val videoUrl: String? = null,
    val gifUrl: String? = null
)
