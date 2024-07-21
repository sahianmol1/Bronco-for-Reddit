package com.anmolsahi.postdetailsdata.repositories

import com.anmolsahi.postdetailsdata.remote.PostDetailsService
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository
import com.bestway.data.mappers.asRedditPostsList
import com.bestway.domain.models.RedditPost

class PostDetailsRepositoryImpl(
    private val service: PostDetailsService
): PostDetailsRepository {
    override suspend fun getPostFromNetwork(postUrl: String): RedditPost {
        return service.getPost(postUrl).getOrThrow().first().asRedditPostsList().first()
    }
}