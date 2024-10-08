package com.anmolsahi.data.mappers

import com.anmolsahi.data.local.SavedPostEntity
import com.anmolsahi.domain.model.SavedPost

internal fun SavedPostEntity.asDomain(): SavedPost = SavedPost(
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

internal fun SavedPost.fromDomain(): SavedPostEntity = SavedPostEntity(
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

internal fun List<SavedPostEntity>.asDomain(): List<SavedPost> {
    return this.map {
        SavedPost(
            id = it.id,
            subName = it.subName,
            title = it.title,
            description = it.description,
            upVotes = it.upVotes,
            comments = it.comments,
            imageUrls = it.imageUrls,
            postUrl = it.postUrl,
            videoUrl = it.videoUrl,
            gifUrl = it.gifUrl,
            author = it.author,
            after = it.after,
        )
    }
}
