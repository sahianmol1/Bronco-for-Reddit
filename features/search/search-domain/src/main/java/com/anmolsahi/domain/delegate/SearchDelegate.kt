package com.anmolsahi.domain.delegate

import com.anmolsahi.domain.models.RedditPost

interface SearchDelegate {
    suspend fun savePost(post: RedditPost)

    suspend fun getSavedPosts(): List<RedditPost>
}