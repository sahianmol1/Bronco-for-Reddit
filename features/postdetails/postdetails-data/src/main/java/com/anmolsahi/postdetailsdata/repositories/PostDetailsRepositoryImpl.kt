package com.anmolsahi.postdetailsdata.repositories

import com.anmolsahi.data.mappers.asDomain
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.postdetailsdata.remote.PostDetailsService
import com.anmolsahi.postdetailsdata.utils.comments
import com.anmolsahi.postdetailsdata.utils.postContent
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository

internal class PostDetailsRepositoryImpl(
    private val service: PostDetailsService,
) : PostDetailsRepository {

    override suspend fun getPostContentFromNetwork(postUrl: String): RedditPost {
        return getPostDetails(postUrl).postContent().asDomain().first()
    }

    override suspend fun getPostComments(postUrl: String): List<RedditPost> {
        return getPostDetails(postUrl).comments().asDomain()
    }

    private suspend fun getPostDetails(postUrl: String) = service.getPost(postUrl)
}
