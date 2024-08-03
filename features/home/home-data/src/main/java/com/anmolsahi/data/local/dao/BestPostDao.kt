package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.entities.BestPostEntity

@Dao
interface BestPostDao {
    @Query("SELECT * FROM best_post")
    suspend fun getAllRedditPosts(): List<BestPostEntity>

    @Upsert
    suspend fun insertRedditPost(bestPostEntity: BestPostEntity)

    @Query("SELECT * FROM best_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): BestPostEntity

    @Delete
    suspend fun deleteRedditPost(bestPostEntity: BestPostEntity)

    @Query("DELETE FROM best_post")
    suspend fun deleteAllRedditPosts()
}
