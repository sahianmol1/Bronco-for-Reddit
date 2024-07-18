package com.bestway.domain.repositories

import com.bestway.domain.model.SavedPost
import kotlinx.coroutines.flow.Flow

interface SavedPostRepository {
    fun getAllSavedPosts(): Flow<List<SavedPost>>

    suspend fun insertPost(post: SavedPost)

    suspend fun getSavedPostById(id: String): SavedPost

    suspend fun deleteSavedPost(id: String)
}
