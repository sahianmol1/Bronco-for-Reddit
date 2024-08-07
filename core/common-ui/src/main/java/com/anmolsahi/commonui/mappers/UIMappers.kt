package com.anmolsahi.commonui.mappers

import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.models.RedditPost

fun RedditPost.asUiModel(): RedditPostUiModel = RedditPostUiModel(
    id = this.id,
    subName = this.subName,
    title = this.title,
    description = this.description,
    upVotes = this.upVotes,
    comments = this.comments,
    imageUrlList = this.imageUrls,
    postUrl = this.postUrl,
    videoUrl = this.videoUrl,
    gifUrl = this.gifUrl,
    author = this.author,
    after = this.after,
    isSaved = this.isSaved,
    thumbnailUrl = this.thumbnailUrl,
    replies = this.replies?.map { it.asUiModel() },
    body = this.body,
)

fun RedditPostUiModel.asDomain() = RedditPost(
    id = this.id,
    subName = this.subName,
    title = this.title,
    description = this.description,
    upVotes = this.upVotes,
    comments = this.comments,
    imageUrls = this.imageUrlList,
    postUrl = this.postUrl,
    videoUrl = this.videoUrl,
    gifUrl = this.gifUrl,
    author = this.author,
    after = this.after,
    isSaved = this.isSaved,
    thumbnailUrl = this.thumbnailUrl,
    body = this.body,
)
