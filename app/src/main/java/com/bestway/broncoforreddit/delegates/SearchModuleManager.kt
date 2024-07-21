package com.bestway.broncoforreddit.delegates

import com.bestway.domain.delegate.SearchDelegate
import com.bestway.domain.models.RedditPost
import com.bestway.domain.repositories.SavedPostRepository
import javax.inject.Inject

class SearchModuleManager
    @Inject
    constructor(
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
    }
