package com.bestway.domain.repositories

import com.bestway.domain.models.RedditPost

interface SearchRepository {

    suspend fun searchReddit(searchQuery: String, after: String): List<RedditPost>
}