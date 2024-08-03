package com.anmolsahi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anmolsahi.data.local.entities.ControversialPostEntity

@Dao
interface ControversialPostDao {
    @Query("SELECT * FROM controversial_post")
    suspend fun getAllRedditPosts(): List<ControversialPostEntity>

    @Upsert
    suspend fun insertRedditPost(controversialPostEntity: ControversialPostEntity)

    @Query("SELECT * FROM controversial_post WHERE id=:id")
    suspend fun getRedditPostById(id: String): ControversialPostEntity

    @Delete
    suspend fun deleteRedditPost(controversialPostEntity: ControversialPostEntity)

    @Query("DELETE FROM controversial_post")
    suspend fun deleteAllRedditPosts()
}