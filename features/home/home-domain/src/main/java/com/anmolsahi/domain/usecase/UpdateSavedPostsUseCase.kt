package com.anmolsahi.domain.usecase

import com.anmolsahi.domain.delegate.HomeDelegate
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.utils.PostType

class UpdateSavedPostsUseCase(
    private val delegate: HomeDelegate,
    private val repository: HomeRepository,
) {

    suspend operator fun invoke(postId: String, shouldSavePost: Boolean, postType: PostType) {
        val post = when (postType) {
            PostType.HOT -> repository.getHotPostById(postId)
            PostType.NEW -> repository.getNewPostById(postId)
            PostType.TOP -> repository.getTopPostById(postId)
            PostType.BEST -> repository.getBestPostById(postId)
            PostType.RISING -> repository.getRisingPostById(postId)
            PostType.CONTROVERSIAL -> repository.getControversialPostById(postId)
        }

        delegate.updateSavedPosts(shouldSavePost, post)
    }
}
