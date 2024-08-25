package com.anmolsahi.data.mappers

import com.anmolsahi.data.model.remote.ListingsResponse
import com.anmolsahi.domain.models.RedditPost

fun ListingsResponse.asDomain(): List<RedditPost> {
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
                imageUrls = this.mediaMetadata?.values?.map { mediaMetadata -> mediaMetadata?.s?.u }
                    ?: listOf(this.preview?.images?.first()?.source?.url),
                postUrl = this.postUrl,
                videoUrl = this.secureMedia?.redditVideo?.videoUrl,
                gifUrl = null,
                author = this.author.orEmpty(),
                thumbnailUrl = this.thumbnailUrl,
                replies = this.replies?.asDomain(),
                body = this.body,
                after = after,
            )
        }
    }.orEmpty()
}
