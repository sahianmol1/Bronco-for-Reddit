package com.bestway.broncoforreddit.data.repositories.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsChildren(
    @SerialName("data")
    val childrenData: ChildrenData
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
    val comments: Int? = null
)
