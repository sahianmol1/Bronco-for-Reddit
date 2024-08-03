package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.delegate.HomeDelegate
import com.anmolsahi.domain.model.SavedPost
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SavedPostRepository
import javax.inject.Inject

class HomeModuleManager @Inject constructor(
    private val savedPostRepository: SavedPostRepository,
) : HomeDelegate {
    override suspend fun updateSavedPosts(shouldSavePost: Boolean, post: RedditPost?) {
        if (shouldSavePost && post != null) {
            savedPostRepository.insertPost(post.asSavedPost())
        } else {
            post?.id?.let {
                savedPostRepository.deleteSavedPost(it)
            }
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
    after = this.after,
)
