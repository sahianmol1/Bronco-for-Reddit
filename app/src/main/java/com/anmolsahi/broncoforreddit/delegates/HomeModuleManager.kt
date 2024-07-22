package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.model.SavedPost
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.presentation.delegate.HomeDelegate
import javax.inject.Inject

class HomeModuleManager
    @Inject
    constructor(
        private val savedPostRepository: SavedPostRepository,
        private val homeRepository: HomeRepository,
    ) : HomeDelegate {
        override suspend fun updateSavedPosts(
            shouldSavePost: Boolean,
            postId: String,
        ) {
            val post = homeRepository.getPostById(postId = postId)
            if (shouldSavePost && post != null) {
                savedPostRepository.insertPost(post.asSavedPost())
            } else {
                savedPostRepository.deleteSavedPost(postId)
            }
        }
    }

fun RedditPost.asSavedPost() =
    SavedPost(
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
        after = this.after,
    )
