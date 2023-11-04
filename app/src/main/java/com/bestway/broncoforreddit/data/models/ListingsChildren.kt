package com.bestway.broncoforreddit.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsChildren(
    @SerialName("data")
    val childrenData: ChildrenData
)

@Serializable
@Entity("hot_posts")
data class ChildrenData(
    @PrimaryKey(autoGenerate = false)
    val roomId: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String? = null,
    @SerialName("selftext")
    val description: String? = null,
    @SerialName("subreddit_name_prefixed")
    val subName: String? = null,
    @SerialName("ups")
    val upVotes: Int? = null,
    @SerialName("num_comments")
    val comments: Int? = null,
    @SerialName("url_overridden_by_dest")
    val imageUrl: String? = null,
    @SerialName("permalink")
    val postUrl: String? = null,
    @SerialName("secure_media")
    @Embedded
    val secureMedia: SecureMedia? = null,
    @SerialName("preview")
    @Embedded
    val gifUrl: GifPreview? = null,
    @SerialName("author")
    val author: String? = null
)

@Serializable
data class SecureMedia(
    @SerialName("reddit_video")
    @Embedded
    val redditVideo: RedditVideo? = null
)

@Serializable
data class RedditVideo(
    @SerialName("dash_url")
    val videoUrl: String? = null
)

@Serializable
data class GifPreview(
    @SerialName("reddit_video_preview")
    @Embedded
    val gifPreview: RedditGifPreview? = null
)

@Serializable
data class RedditGifPreview(
    @SerialName("dash_url")
    val url: String? = null
)