package com.bestway.presentation.delegate

interface HomeDelegate {
    suspend fun updateSavedPosts(shouldSavePost: Boolean, postId: String)
}