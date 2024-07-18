package com.bestway.data

import com.bestway.data.apihelper.getSafeResponse
import com.bestway.domain.models.RedditPost
import com.bestway.domain.repositories.SearchRepository
import io.ktor.client.HttpClient

class SearchRepositoryImpl(
    private val client: HttpClient,
) : SearchRepository {
    override suspend fun searchReddit(
        searchQuery: String,
        after: String,
    ): List<RedditPost> {
        client.getSafeResponse("")
    }
}
