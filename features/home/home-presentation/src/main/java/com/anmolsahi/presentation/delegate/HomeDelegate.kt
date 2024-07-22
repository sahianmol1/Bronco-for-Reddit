package com.anmolsahi.presentation.delegate

interface HomeDelegate {
    suspend fun updateSavedPosts(
        shouldSavePost: Boolean,
        postId: String,
    )
}
