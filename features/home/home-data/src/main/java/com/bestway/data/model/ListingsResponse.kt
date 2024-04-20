package com.bestway.data.model

import com.bestway.data.local.entity.RedditPostEntity
import com.bestway.domain.model.RedditPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse(
    @SerialName("kind") val kind: String? = null,
    @SerialName("data") val data: ListingsData? = null
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
                after = after
            )
        }
    }.orEmpty()
}

fun List<RedditPostEntity>.asDomain(): List<RedditPost>? {
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
            after = it.after
        )
    }
}