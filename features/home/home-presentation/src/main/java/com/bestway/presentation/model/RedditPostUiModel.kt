package com.bestway.presentation.model

import androidx.compose.runtime.Immutable
import com.bestway.domain.model.RedditPost
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
    val imageUrl: String? = null,
    val postUrl: String? = null,
    val videoUrl: String? = null,
    val gifUrl: String? = null,
    val author: String = ""
)

fun RedditPost.asUiModel(): RedditPostUiModel {
    return RedditPostUiModel(
        id = this.id,
        subName = this.subName,
        title = this.title,
        description = this.description,
        upVotes = this.upVotes,
        comments = this.comments,
        imageUrl = this.imageUrl,
        postUrl = this.postUrl,
        videoUrl = this.videoUrl,
        gifUrl = this.gifUrl,
        author = this.author
    )
}
