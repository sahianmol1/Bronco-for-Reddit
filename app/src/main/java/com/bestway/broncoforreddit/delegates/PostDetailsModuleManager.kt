package com.bestway.broncoforreddit.delegates

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.bestway.domain.model.SavedPost
import com.bestway.domain.models.RedditPost
import com.bestway.domain.repositories.HomeRepository
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.presentation.delegate.HomeDelegate
import javax.inject.Inject

class PostDetailsModuleManager
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
        private val savedPostsRepository: SavedPostRepository,
        private val homeDelegate: HomeDelegate,
    ) : PostDetailsDelegate {
        override suspend fun getPostById(
            postId: String,
            isSavedPostsFlow: Boolean,
        ): RedditPost =
            if (isSavedPostsFlow) {
                savedPostsRepository.getSavedPostById(postId).toRedditPost()
            } else {
                homeRepository.getPostById(postId)
            }

        override suspend fun togglePostSavedStatus(postId: String): Boolean = homeRepository.togglePostSavedStatus(postId)

        override suspend fun deleteSavedPost(postId: String) = savedPostsRepository.deleteSavedPost(postId)

        override suspend fun updateSavedPosts(
            shouldSavePost: Boolean,
            postId: String,
        ) {
            homeDelegate.updateSavedPosts(shouldSavePost, postId)
        }

        private fun SavedPost.toRedditPost(): RedditPost =
            RedditPost(
                id = id,
                subName = subName,
                title = title,
                description = description,
                upVotes = upVotes,
                comments = comments,
                imageUrl = imageUrl,
                postUrl = postUrl,
                videoUrl = videoUrl,
                gifUrl = gifUrl,
                author = author,
                after = after,
            )
    }
