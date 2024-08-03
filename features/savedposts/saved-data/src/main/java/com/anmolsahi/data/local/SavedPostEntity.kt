package com.anmolsahi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_post")
data class SavedPostEntity(
    @PrimaryKey
    val id: String,
    val subName: String = "",
    val title: String? = null,
    val description: String? = null,
    val upVotes: Int = 0,
    val comments: Int = 0,
    val imageUrls: List<String?>? = null,
    val postUrl: String? = null,
    val videoUrl: String? = null,
    val gifUrl: String? = null,
    val author: String = "",
    val after: String? = null,
)
