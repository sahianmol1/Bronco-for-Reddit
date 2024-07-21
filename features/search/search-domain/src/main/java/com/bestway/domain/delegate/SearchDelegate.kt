package com.bestway.domain.delegate

import com.bestway.domain.models.RedditPost

interface SearchDelegate {
    suspend fun savePost(post: RedditPost)
}