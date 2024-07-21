package com.bestway.domain.model

import com.bestway.domain.models.RedditPost

data class SavedPost(
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
    val author: String = "",
    val after: String? = null,
    val thumbnailUrl: String? = null,
)

fun SavedPost.asRedditPost() = RedditPost(
    id = id,
    subName = subName,
    title = title,
    description = description,
    upVotes = upVotes,
    comments = comments,
    imageUrl = imageUrl,
    postUrl = postUrl,
    videoUrl = videoUrl,
    gifUrl = gifUrl,
    author = author,
    after = after,
    thumbnailUrl = thumbnailUrl,
)
