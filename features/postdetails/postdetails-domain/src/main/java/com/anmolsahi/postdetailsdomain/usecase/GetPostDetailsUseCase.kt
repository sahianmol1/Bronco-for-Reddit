package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository
import com.bestway.domain.models.RedditPost

class GetPostDetailsUseCase(
    private val delegate: PostDetailsDelegate,
    private val repository: PostDetailsRepository,
) {
    suspend operator fun invoke(
        postId: String,
        postUrl: String,
        isSavedPostsFlow: Boolean,
    ): RedditPost {
        return delegate.getPostById(postId, isSavedPostsFlow) ?: repository.getPostFromNetwork(postUrl)
    }
}
