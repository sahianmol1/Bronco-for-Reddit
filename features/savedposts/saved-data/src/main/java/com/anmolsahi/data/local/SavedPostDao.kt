package com.anmolsahi.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPostDao {
    @Upsert
    suspend fun insertPost(post: SavedPostEntity)

    @Query("SELECT * FROM saved_post")
    fun getAllSavedPosts(): Flow<List<SavedPostEntity>>

    @Query("SELECT * FROM saved_post WHERE id=:id")
    suspend fun getSavedPostById(id: String): SavedPostEntity?

    @Query("DELETE FROM saved_post WHERE id=:id")
    suspend fun deleteSavedPost(id: String)
}
