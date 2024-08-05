package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.entities.HotPostEntity

@Dao
interface HotPostDao {
    @Query("SELECT * FROM hot_post")
    suspend fun getAllRedditPosts(): List<HotPostEntity>

    @Upsert
    suspend fun insertRedditPost(hotPostEntity: HotPostEntity)

    @Query("SELECT * FROM hot_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): HotPostEntity

    @Delete
    suspend fun deleteRedditPost(hotPostEntity: HotPostEntity)

    @Query("DELETE FROM hot_post")
    suspend fun deleteAllRedditPosts()

    @Query("SELECT id FROM hot_post ORDER BY createdAt ASC LIMIT :count")
    // here last means the new/latest entries that were added to the end of the list
    suspend fun getLastNPostIdList(count: Int): List<String>

    @Query("DELETE FROM hot_post WHERE id IN (:ids)")
    suspend fun deleteStalePosts(ids: List<String>)
}
