package com.anmolsahi.data.mappers

import com.anmolsahi.data.local.entities.NewPostEntity
import com.anmolsahi.data.model.remote.ListingsResponse
import com.anmolsahi.domain.models.RedditPost

internal fun ListingsResponse.asNewPostEntity(): List<NewPostEntity> {
    val after = this.data?.after
    return this.data?.children?.map {
        it.childrenData.run {
            NewPostEntity(
                id = this.id,
                subName = this.subName.orEmpty(),
                title = this.title,
                description = this.description,
                upVotes = this.upVotes ?: 0,
                comments = this.comments ?: 0,
                imageUrls = this.mediaMetadata?.values?.map { mediaMetadata -> mediaMetadata?.s?.u }
                    ?: listOf(this.preview?.images?.first()?.source?.url),
                postUrl = this.postUrl,
                videoUrl = this.secureMedia?.redditVideo?.videoUrl,
                gifUrl = "",
                author = this.author.orEmpty(),
                thumbnailUrl = this.thumbnailUrl,
                after = after,
            )
        }
    }.orEmpty()
}

internal fun List<NewPostEntity>.asDomain(): List<RedditPost> {
    return this.map {
        RedditPost(
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
            isSaved = it.isSaved,
            thumbnailUrl = it.thumbnailUrl,
        )
    }
}

internal fun NewPostEntity.asDomain(): RedditPost {
    return RedditPost(
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
        isSaved = this.isSaved,
        thumbnailUrl = this.thumbnailUrl,
    )
}
