package com.anmolsahi.postdetailsdomain.delegate

import com.bestway.domain.models.RedditPost


interface PostDetailsDelegate {
    suspend fun getPostById(postId: String): RedditPost

    suspend fun togglePostSavedStatus(postId: String): Boolean

    suspend fun deleteSavedPost(postId: String)

    suspend fun updateSavedPosts(shouldSavePost: Boolean, postId: String)
}