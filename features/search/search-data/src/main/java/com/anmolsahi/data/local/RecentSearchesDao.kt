package com.anmolsahi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchesDao {

    @Upsert
    suspend fun insert(recentSearch: RecentSearchEntity)

    @Delete
    suspend fun delete(recentSearch: RecentSearchEntity)

    @Query("SELECT * FROM recent_searches")
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>
}