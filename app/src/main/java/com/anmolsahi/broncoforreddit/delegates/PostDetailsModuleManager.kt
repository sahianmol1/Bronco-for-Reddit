package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.model.SavedPost
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.presentation.delegate.HomeDelegate
import javax.inject.Inject

class PostDetailsModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
    private val savedPostsRepository: SavedPostRepository,
    private val homeDelegate: HomeDelegate,
) : PostDetailsDelegate {
    override suspend fun getPostById(postId: String): RedditPost? =
        savedPostsRepository.getSavedPostById(postId)?.toRedditPost()?.copy(isSaved = true)
            ?: homeRepository.getPostById(postId)

    override suspend fun togglePostSavedStatus(postId: String): Boolean =
        homeRepository.togglePostSavedStatus(postId)

    override suspend fun deleteSavedPost(postId: String) =
        savedPostsRepository.deleteSavedPost(postId)

    override suspend fun updateSavedPosts(shouldSavePost: Boolean, postId: String) {
        homeDelegate.updateSavedPosts(shouldSavePost, postId)
    }

    override suspend fun togglePostSavedStatusInDb(post: RedditPost?): Boolean {
        // try to update the post saved status in the home database
        // It will handle the required exceptions if the post is not available in the home database,
        // For example, if the post details were reached from Search Posts screen,
        // this will fail and handle the exception internally
        homeRepository.togglePostSavedStatus(post?.id.orEmpty())

        // Toggle the post saved status in the saved posts database
        return savedPostsRepository.togglePostSavedStatusInDb(post?.asSavedPost())
    }

    private fun SavedPost.toRedditPost(): RedditPost = RedditPost(
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
