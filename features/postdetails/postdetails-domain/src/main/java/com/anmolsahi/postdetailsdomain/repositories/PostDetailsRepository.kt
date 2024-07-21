package com.anmolsahi.postdetailsdomain.repositories

import com.bestway.domain.models.RedditPost

interface PostDetailsRepository {
    suspend fun getPostFromNetwork(postUrl: String): RedditPost
}