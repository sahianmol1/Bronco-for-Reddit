package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.entities.TopPostEntity

@Dao
interface TopPostDao {
    @Query("SELECT * FROM top_post")
    suspend fun getAllRedditPosts(): List<TopPostEntity>

    @Upsert
    suspend fun insertRedditPost(topPostEntity: TopPostEntity)

    @Query("SELECT * FROM top_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): TopPostEntity

    @Delete
    suspend fun deleteRedditPost(topPostEntity: TopPostEntity)

    @Query("DELETE FROM top_post")
    suspend fun deleteAllRedditPosts()
}