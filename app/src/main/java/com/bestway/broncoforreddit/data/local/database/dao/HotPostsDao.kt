package com.bestway.broncoforreddit.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bestway.broncoforreddit.data.models.ChildrenData

@Dao
interface HotPostsDao {

    @Query("SELECT * FROM hot_posts ")
    fun getAllPosts(): PagingSource<Int, ChildrenData>

    @Upsert
    suspend fun insertAll(posts: ChildrenData)

    @Query("DELETE FROM hot_posts")
    suspend fun deleteAllPosts()

}
