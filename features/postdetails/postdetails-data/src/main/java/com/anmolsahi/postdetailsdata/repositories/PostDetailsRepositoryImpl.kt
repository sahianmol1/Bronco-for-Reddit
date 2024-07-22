package com.anmolsahi.postdetailsdata.repositories

import com.anmolsahi.data.mappers.asRedditPostsList
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.postdetailsdata.remote.PostDetailsService
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository

class PostDetailsRepositoryImpl(
    private val service: PostDetailsService,
) : PostDetailsRepository {
    override suspend fun getPostFromNetwork(postUrl: String): RedditPost {
        return service.getPost(postUrl).getOrThrow().first().asRedditPostsList().first()
    }
}
