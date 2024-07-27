package com.anmolsahi.postdetailsdomain.repositories

import com.anmolsahi.domain.models.RedditPost

interface PostDetailsRepository {
    suspend fun getPostContentFromNetwork(postUrl: String): RedditPost

    suspend fun getPostComments(postUrl: String): List<RedditPost>
}
