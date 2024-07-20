package com.bestway.data

import com.bestway.data.local.RecentSearchesDao
import com.bestway.data.mapper.asDomain
import com.bestway.data.mapper.asEntity
import com.bestway.data.model.remote.asRedditPost
import com.bestway.data.remote.SearchService
import com.bestway.domain.model.RecentSearch
import com.bestway.domain.models.RedditPost
import com.bestway.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val dao: RecentSearchesDao,
    private val service: SearchService,
) : SearchRepository {
    override fun searchReddit(
        query: String,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            emit(service.searchReddit(query, nextPageKey).getOrThrow().asRedditPost())
        }
    }

    override fun getRecentSearches(): Flow<List<RecentSearch>> {
        return dao.getRecentSearches()
            .map { recentSearches -> recentSearches.map { it.asDomain() } }
    }

    override suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        dao.insert(recentSearch.asEntity())
    }

    override suspend fun deleteRecentSearch(recentSearch: RecentSearch) {
        dao.delete(recentSearch.asEntity())
    }
}
