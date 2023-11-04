package com.bestway.broncoforreddit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hot_posts_remote_keys")
data class HotPostsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val before: String?,
    val after: String?
)
