package com.bestway.broncoforreddit.delegates

import com.bestway.domain.model.RedditPost
import com.bestway.domain.model.SavedPost
import com.bestway.domain.repositories.HomeRepository
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.presentation.delegate.HomeDelegate
import javax.inject.Inject

class HomeModuleManager @Inject constructor(
    private val savedPostRepository: SavedPostRepository,
    private val homeRepository: HomeRepository,
) : HomeDelegate {
    override suspend fun updateSavedPosts(shouldSavePost: Boolean, postId: String) {
        val post = homeRepository.getPostById(postId = postId)
        if (shouldSavePost) {
            savedPostRepository.insertPost(post.asSavedPost())
        } else {
            savedPostRepository.deleteSavedPost(post.asSavedPost())
        }
    }
}

fun RedditPost.asSavedPost() = SavedPost(
    id = this.id,
    subName = this.subName,
    title = this.title,
    description = this.description,
    upVotes = this.upVotes,
    comments = this.comments,
    imageUrl = this.imageUrl,
    postUrl = this.postUrl,
    videoUrl = this.videoUrl,
    gifUrl = this.gifUrl,
    author = this.author,
    after = this.after
)