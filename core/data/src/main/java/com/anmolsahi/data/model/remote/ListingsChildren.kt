package com.anmolsahi.data.model.remote

import com.anmolsahi.data.model.remote.mediametada.MediaMetadata
import com.anmolsahi.data.model.remote.preview.Preview
import com.anmolsahi.data.utils.serializers.RepliesDeserializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsChildren(
    @SerialName("data")
    val childrenData: ChildrenData,
)

@Serializable
data class ChildrenData(
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
    @SerialName("permalink")
    val postUrl: String? = null,
    @SerialName("secure_media")
    val secureMedia: SecureMedia? = null,
    @SerialName("author")
    val author: String? = null,
    @SerialName("thumbnail")
    val thumbnailUrl: String? = null,
    @SerialName("preview")
    val preview: Preview? = null,
    @SerialName("replies")
    @Serializable(with = RepliesDeserializer::class)
    val replies: ListingsResponse? = null,
    @SerialName("body")
    val body: String? = null,
    @SerialName("media_metadata")
    val mediaMetadata: Map<String, MediaMetadata?>? = null,
)

@Serializable
data class SecureMedia(
    @SerialName("reddit_video")
    val redditVideo: RedditVideo? = null,
)

@Serializable
data class RedditVideo(
    @SerialName("dash_url")
    val videoUrl: String? = null,
)

@Serializable
data class GifPreview(
    @SerialName("reddit_video_preview")
    val gifPreview: RedditGifPreview? = null,
)

@Serializable
data class RedditGifPreview(
    @SerialName("dash_url")
    val url: String? = null,
)
