package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.entities.NewPostEntity

@Dao
interface NewPostDao {
    @Query("SELECT * FROM new_post")
    suspend fun getAllRedditPosts(): List<NewPostEntity>

    @Upsert
    suspend fun insertRedditPost(newPostEntity: NewPostEntity)

    @Query("SELECT * FROM new_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): NewPostEntity

    @Delete
    suspend fun deleteRedditPost(newPostEntity: NewPostEntity)

    @Query("DELETE FROM new_post")
    suspend fun deleteAllRedditPosts()

    @Query("SELECT id FROM new_post ORDER BY createdAt ASC LIMIT :count")
    // here last means the new/latest entries that were added to the end of the list
    suspend fun getLastNPostIdList(count: Int): List<String>

    @Query("DELETE FROM new_post WHERE id IN (:ids)")
    suspend fun deleteStalePosts(ids: List<String>)
}
