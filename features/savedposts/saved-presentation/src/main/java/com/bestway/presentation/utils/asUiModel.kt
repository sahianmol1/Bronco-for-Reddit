package com.bestway.presentation.utils

import com.anmolsahi.commonui.models.RedditPostUiModel
import com.bestway.domain.model.SavedPost

fun List<SavedPost>.asUiModel(): List<RedditPostUiModel> {
    return this.map {
        RedditPostUiModel(
            id = it.id,
            subName = it.subName,
            title = it.title,
            description = it.description,
            upVotes = it.upVotes,
            comments = it.comments,
            imageUrl = it.imageUrl,
            postUrl = it.postUrl,
            videoUrl = it.videoUrl,
            gifUrl = it.gifUrl,
            author = it.author,
            after = it.after
        )
    }
}