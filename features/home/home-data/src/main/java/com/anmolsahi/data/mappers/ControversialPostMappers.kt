package com.anmolsahi.data.mappers

import com.anmolsahi.data.local.entities.ControversialPostEntity
import com.anmolsahi.data.model.remote.ListingsResponse
import com.anmolsahi.domain.models.RedditPost

fun ListingsResponse.asControversialPostEntity(): List<ControversialPostEntity> {
    val after = this.data?.after
    return this.data?.children?.map {
        it.childrenData.run {
            ControversialPostEntity(
                id = this.id,
                subName = this.subName.orEmpty(),
                title = this.title,
                description = this.description,
                upVotes = this.upVotes ?: 0,
                comments = this.comments ?: 0,
                imageUrl = this.preview?.images?.first()?.source?.url,
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

fun List<com.anmolsahi.data.local.entities.ControversialPostEntity>.asDomain(): List<RedditPost> {
    return this.map {
        RedditPost(
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
            after = it.after,
            isSaved = it.isSaved,
            thumbnailUrl = it.thumbnailUrl,
        )
    }
}

fun ControversialPostEntity.asDomain(): RedditPost {
    return RedditPost(
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
        isSaved = this.isSaved,
        thumbnailUrl = this.thumbnailUrl,
    )
}
