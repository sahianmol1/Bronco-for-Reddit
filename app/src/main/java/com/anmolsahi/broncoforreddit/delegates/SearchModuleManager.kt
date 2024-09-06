package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SavedPostRepository
import javax.inject.Inject

class SearchModuleManager @Inject constructor(
    private val savePostRepository: SavedPostRepository,
) : SearchDelegate {
    override suspend fun savePost(post: RedditPost) {
        val savedPost = savePostRepository.getSavedPostById(post.id)

        if (savedPost == null) {
            savePostRepository.insertPost(post.asSavedPost())
        } else {
            savePostRepository.deleteSavedPost(post.id)
        }
    }

    override suspend fun getSavedPosts(): List<RedditPost> = emptyList()
//        savePostRepository.getAllSavedPosts().first().map { it.asRedditPost() }
}
