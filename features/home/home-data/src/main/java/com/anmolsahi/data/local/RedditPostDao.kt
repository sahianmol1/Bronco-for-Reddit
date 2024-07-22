package com.anmolsahi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.model.local.RedditPostEntity

@Dao
interface RedditPostDao {
    @Query("SELECT * FROM reddit_post")
    suspend fun getAllRedditPosts(): List<RedditPostEntity>

    @Upsert
    suspend fun insertRedditPost(redditPostEntity: RedditPostEntity)

    @Query("SELECT * FROM reddit_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): RedditPostEntity

    @Delete
    suspend fun deleteRedditPost(redditPostEntity: RedditPostEntity)

    @Query("DELETE FROM reddit_post")
    suspend fun deleteAllRedditPosts()
}
