package com.bestway.data.model.remote

import com.bestway.data.model.local.RedditPostEntity
import com.bestway.domain.models.RedditPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse(
    @SerialName("kind") val kind: String? = null,
    @SerialName("data") val data: ListingsData? = null,
)

fun ListingsResponse.asEntity(): List<RedditPostEntity> {
    val after = this.data?.after
    return this.data?.children?.map {
        it.childrenData.run {
            RedditPostEntity(
                id = this.id,
                subName = this.subName.orEmpty(),
                title = this.title,
                description = this.description,
                upVotes = this.upVotes ?: 0,
                comments = this.comments ?: 0,
                imageUrl = this.imageUrl,
                postUrl = this.postUrl,
                videoUrl = this.secureMedia?.redditVideo?.videoUrl,
                gifUrl = this.gifUrl?.gifPreview?.url.orEmpty(),
                author = this.author.orEmpty(),
                thumbnailUrl = this.thumbnailUrl,
                after = after,
            )
        }
    }.orEmpty()
}

fun ListingsResponse.asRedditPosts(): List<RedditPost> {
    val after = this.data?.after
    return this.data?.children?.map {
        it.childrenData.run {
            RedditPost(
                id = this.id,
                subName = this.subName.orEmpty(),
                title = this.title,
                description = this.description,
                upVotes = this.upVotes ?: 0,
                comments = this.comments ?: 0,
                imageUrl = this.imageUrl,
                postUrl = this.postUrl,
                videoUrl = this.secureMedia?.redditVideo?.videoUrl,
                gifUrl = this.gifUrl?.gifPreview?.url.orEmpty(),
                author = this.author.orEmpty(),
                thumbnailUrl = this.thumbnailUrl,
                after = after,
            )
        }
    }.orEmpty()
}

fun List<RedditPostEntity>.asDomain(): List<RedditPost> {
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

fun RedditPostEntity.asDomain(): RedditPost {
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
