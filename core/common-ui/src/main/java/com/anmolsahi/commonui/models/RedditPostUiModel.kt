package com.anmolsahi.commonui.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class RedditPostUiModel(
    val id: String,
    val subName: String = "",
    val title: String? = null,
    val description: String? = null,
    val upVotes: Int = 0,
    val comments: Int = 0,
    val imageUrlList: List<String?>? = null,
    val postUrl: String? = null,
    val videoUrl: String? = null,
    val gifUrl: String? = null,
    val author: String = "",
    val after: String? = null,
    val isSaved: Boolean = false,
    val thumbnailUrl: String? = null,
    val replies: List<RedditPostUiModel>? = null,
    val body: String? = null,
)
