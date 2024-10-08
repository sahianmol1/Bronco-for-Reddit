package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.dao.contract.IHomePostDao
import com.anmolsahi.data.local.entities.BestPostEntity

@Dao
interface BestPostDao : IHomePostDao {
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

    @Query("SELECT id FROM best_post ORDER BY createdAt ASC LIMIT :count")
    // here last means the new entries that were added to the end of the list
    suspend fun getLastNPostIdList(count: Int): List<String>

    @Query("DELETE FROM best_post WHERE id IN (:ids)")
    suspend fun deleteStalePosts(ids: List<String>)
}
