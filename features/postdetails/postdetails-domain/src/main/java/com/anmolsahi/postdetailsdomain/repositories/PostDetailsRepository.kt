package com.anmolsahi.postdetailsdomain.repositories

import com.anmolsahi.domain.models.RedditPost

interface PostDetailsRepository {
    suspend fun getPostFromNetwork(postUrl: String): RedditPost
}
