package com.anmolsahi.data.repositories

import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.mapper.asDomain
import com.anmolsahi.data.mapper.asEntity
import com.anmolsahi.data.mappers.asDomain
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val dao: RecentSearchesDao,
    private val service: SearchService,
) : SearchRepository {
    override fun searchReddit(query: String, nextPageKey: String?): Flow<List<RedditPost>?> {
        return flow {
            if (query.isBlank()) {
                emit(null)
            } else {
                emit(service.searchReddit(query, nextPageKey).getOrThrow().asDomain())
            }
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
