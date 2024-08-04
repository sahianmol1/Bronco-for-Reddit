package com.anmolsahi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anmolsahi.data.local.entities.contract.IHomePostEntity

@Entity(tableName = "best_post")
data class BestPostEntity(
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
    val isSaved: Boolean = false,
    val thumbnailUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
) : IHomePostEntity
