package com.bestway.broncoforreddit.delegates

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.bestway.domain.models.RedditPost
import com.bestway.domain.repositories.HomeRepository
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.presentation.delegate.HomeDelegate
import javax.inject.Inject

class PostDetailsModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
    private val savedPostsRepository: SavedPostRepository,
    private val homeDelegate: HomeDelegate,
) : PostDetailsDelegate {
    override suspend fun getPostById(postId: String): RedditPost {
        return homeRepository.getPostById(postId)
    }

    override suspend fun togglePostSavedStatus(postId: String): Boolean {
        return homeRepository.togglePostSavedStatus(postId)
    }

    override suspend fun deleteSavedPost(postId: String) {
        return savedPostsRepository.deleteSavedPost(postId)
    }

    override suspend fun updateSavedPosts(shouldSavePost: Boolean, postId: String) {
        homeDelegate.updateSavedPosts(shouldSavePost, postId)
    }
}