package com.anmolsahi.presentation.utils

import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.model.SavedPost

internal fun List<SavedPost>.asUiModel(): List<RedditPostUiModel> {
    return this.map {
        RedditPostUiModel(
            id = it.id,
            subName = it.subName,
            title = it.title,
            description = it.description,
            upVotes = it.upVotes,
            comments = it.comments,
            imageUrlList = it.imageUrls,
            postUrl = it.postUrl,
            videoUrl = it.videoUrl,
            gifUrl = it.gifUrl,
            author = it.author,
            after = it.after,
        )
    }
}
