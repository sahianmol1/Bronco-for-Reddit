package com.bestway.presentation.utils

import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.bestway.domain.model.RedditPost

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
        author = this.author,
        after = this.after,
        isSaved = this.isSaved
    )
}