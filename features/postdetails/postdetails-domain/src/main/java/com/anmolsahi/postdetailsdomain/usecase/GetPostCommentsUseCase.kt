package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository

class GetPostCommentsUseCase(
    private val delegate: PostDetailsDelegate,
    private val repository: PostDetailsRepository,
) {
    suspend operator fun invoke(postId: String, postUrl: String): List<RedditPost> {
        val url = delegate.getPostById(postId)?.postUrl ?: postUrl

        return repository.getPostComments(postUrl = url)
    }
}
