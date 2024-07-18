package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.bestway.domain.models.RedditPost

class GetPostDetailsUseCase(
    private val delegate: PostDetailsDelegate,
) {
    suspend operator fun invoke(
        postId: String,
        isSavedPostsFlow: Boolean,
    ): RedditPost = delegate.getPostById(postId, isSavedPostsFlow)
}
