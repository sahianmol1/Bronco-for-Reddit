package com.anmolsahi.postdetailsdomain.repositories

import com.anmolsahi.domain.models.RedditPost

interface PostDetailsRepository {
    suspend fun getPostDetailsFromNetwork(postUrl: String): RedditPost
}
