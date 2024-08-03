package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.model.SavedPost
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import javax.inject.Inject

class PostDetailsModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
    private val savedPostsRepository: SavedPostRepository,
) : PostDetailsDelegate {
    override suspend fun getPostById(postId: String): RedditPost? =
        savedPostsRepository.getSavedPostById(postId)?.toRedditPost()?.copy(isSaved = true)
            ?: homeRepository.getHotPostById(postId)
            ?: homeRepository.getNewPostById(postId)
            ?: homeRepository.getTopPostById(postId)
            ?: homeRepository.getBestPostById(postId)
            ?: homeRepository.getRisingPostById(postId)
            ?: homeRepository.getControversialPostById(postId)

    override suspend fun togglePostSavedStatus(postId: String) {
        // This will try to toggle the post saved status for all the posts
        // in the home page including all tabs and handle exceptions internally
        homeRepository.run {
            toggleHotPostSavedStatus(postId)
            toggleNewPostSavedStatus(postId)
            toggleTopPostSavedStatus(postId)
            toggleBestPostSavedStatus(postId)
            toggleRisingPostSavedStatus(postId)
            toggleControversialPostSavedStatus(postId)
        }
    }

    override suspend fun deleteSavedPost(postId: String) =
        savedPostsRepository.deleteSavedPost(postId)

    override suspend fun togglePostSavedStatusInDb(post: RedditPost?): Boolean {
        // try to update the post saved status in the home database
        // It will handle the required exceptions if the post is not available in the home database,
        // For example, if the post details were reached from Search Posts screen,
        // this will fail and handle the exception internally
        homeRepository.run {
            toggleHotPostSavedStatus(post?.id.orEmpty())
            toggleNewPostSavedStatus(post?.id.orEmpty())
            toggleTopPostSavedStatus(post?.id.orEmpty())
            toggleBestPostSavedStatus(post?.id.orEmpty())
            toggleRisingPostSavedStatus(post?.id.orEmpty())
            toggleControversialPostSavedStatus(post?.id.orEmpty())
        }

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
