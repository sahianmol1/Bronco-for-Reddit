package com.anmolsahi.broncoforreddit.utils

import com.anmolsahi.domain.model.SavedPost
import com.anmolsahi.domain.models.RedditPost

fun RedditPost.asSavedPost() = SavedPost(
    id = this.id,
    subName = this.subName,
    title = this.title,
    description = this.description,
    upVotes = this.upVotes,
    comments = this.comments,
    imageUrls = this.imageUrls,
    postUrl = this.postUrl,
    videoUrl = this.videoUrl,
    gifUrl = this.gifUrl,
    author = this.author,
    after = this.after,
)

fun SavedPost.toRedditPost(): RedditPost = RedditPost(
    id = id,
    subName = subName,
    title = title,
    description = description,
    upVotes = upVotes,
    comments = comments,
    imageUrls = imageUrls,
    postUrl = postUrl,
    videoUrl = videoUrl,
    gifUrl = gifUrl,
    author = author,
    after = after,
)
