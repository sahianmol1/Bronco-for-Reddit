package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.broncoforreddit.utils.asSavedPost
import com.anmolsahi.domain.delegate.HomeDelegate
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
