package com.anmolsahi.domain.repositories

import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.models.RedditPost
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchReddit(query: String, nextPageKey: String? = null): Flow<List<RedditPost>?>

    fun getRecentSearches(): Flow<List<RecentSearch>>

    suspend fun insertRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteAllRecentSearches()
}
