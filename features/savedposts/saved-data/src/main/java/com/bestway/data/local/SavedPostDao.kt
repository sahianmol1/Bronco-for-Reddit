package com.bestway.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SavedPostDao {

    @Upsert
    suspend fun insertPost(post: SavedPostEntity)

    @Query("SELECT * FROM saved_post")
    suspend fun getAllSavedPosts(): List<SavedPostEntity>

    @Query("SELECT * FROM saved_post WHERE id=:id")
    suspend fun getSavedPostById(id: String): SavedPostEntity

    @Delete
    suspend fun deleteSavedPost(post: SavedPostEntity)
}