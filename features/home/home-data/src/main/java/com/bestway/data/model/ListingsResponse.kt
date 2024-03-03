package com.bestway.data.model

import com.bestway.domain.model.RedditPosts
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse(
    @SerialName("kind") val kind: String? = null,
    @SerialName("data") val data: ListingsData? = null
)

fun ListingsResponse.asDomain(): List<RedditPosts> {
    return this.data?.children?.map {
        it.childrenData.run {
            RedditPosts(
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
                author = this.author.orEmpty()
            )
        }
    }.orEmpty()
}
