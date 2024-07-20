package com.bestway.data

import com.bestway.data.local.RecentSearchesDao
import com.bestway.data.mapper.asDomain
import com.bestway.data.mapper.asEntity
import com.bestway.domain.model.RecentSearch
import com.bestway.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val dao: RecentSearchesDao,
) : SearchRepository {
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
