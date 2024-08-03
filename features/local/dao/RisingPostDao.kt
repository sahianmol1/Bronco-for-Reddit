package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.entities.RisingPostEntity

@Dao
interface RisingPostDao {
    @Query("SELECT * FROM rising_post")
    suspend fun getAllRedditPosts(): List<RisingPostEntity>

    @Upsert
    suspend fun insertRedditPost(risingPostEntity: RisingPostEntity)

    @Query("SELECT * FROM rising_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): RisingPostEntity

    @Delete
    suspend fun deleteRedditPost(risingPostEntity: RisingPostEntity)

    @Query("DELETE FROM rising_post")
    suspend fun deleteAllRedditPosts()
}