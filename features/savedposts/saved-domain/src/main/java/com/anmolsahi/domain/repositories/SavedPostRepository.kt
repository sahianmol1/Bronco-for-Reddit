package com.anmolsahi.domain.repositories

import com.anmolsahi.domain.model.SavedPost
import kotlinx.coroutines.flow.Flow

interface SavedPostRepository {
    fun getAllSavedPosts(): Flow<List<SavedPost>>

    suspend fun insertPost(post: SavedPost)

    suspend fun getSavedPostById(id: String): SavedPost?

    suspend fun deleteSavedPost(id: String)

    suspend fun togglePostSavedStatusInDb(post: SavedPost?): Boolean
}
