package com.bestway.broncoforreddit.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bestway.broncoforreddit.data.local.models.HotPost
import kotlinx.coroutines.flow.Flow

@Dao
interface HotPostsDao {

    @Upsert
    suspend fun insertAllPosts(hotPost: List<HotPost>)
    @Query("SELECT * FROM hot_posts")
    fun getAllPosts(): Flow<List<HotPost>>

    @Query("DELETE FROm hot_posts")
    suspend fun deleteAllPosts()

    @Query("SELECT * FROM hot_posts WHERE postId=:id")
    suspend fun getPostById(id: String): HotPost
}