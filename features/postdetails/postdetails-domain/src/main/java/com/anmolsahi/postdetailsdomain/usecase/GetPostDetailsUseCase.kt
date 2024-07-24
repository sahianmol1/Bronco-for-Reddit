package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository

class GetPostDetailsUseCase(
    private val delegate: PostDetailsDelegate,
    private val repository: PostDetailsRepository,
) {
    suspend operator fun invoke(postId: String, postUrl: String): RedditPost {
        return delegate.getPostById(postId) ?: repository.getPostDetailsFromNetwork(postUrl)
    }
}
