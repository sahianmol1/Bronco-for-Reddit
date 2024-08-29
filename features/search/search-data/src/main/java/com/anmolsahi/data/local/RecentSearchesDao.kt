package com.anmolsahi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchesDao {
    @Upsert
    suspend fun upsert(recentSearch: RecentSearchEntity)

    @Delete
    suspend fun delete(recentSearch: RecentSearchEntity)

    @Query("SELECT * FROM recent_searches ORDER BY timestamp DESC")
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    @Query("DELETE from recent_searches")
    suspend fun deleteAll()
}
