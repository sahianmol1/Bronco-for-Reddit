package com.bestway.domain.delegate

interface SavedPostDelegate {
    suspend fun togglePostSavedStatus(postId: String): Boolean
}
