package com.bestway.broncoforreddit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bestway.broncoforreddit.data.models.HotPostsRemoteKeys

@Dao
interface HotPostsRemoteKeysDao {

    @Query("SELECT * FROM hot_posts_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): HotPostsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<HotPostsRemoteKeys>)

    @Query("DELETE FROM hot_posts_remote_keys")
    suspend fun deleteAllRemoteKeys()
}