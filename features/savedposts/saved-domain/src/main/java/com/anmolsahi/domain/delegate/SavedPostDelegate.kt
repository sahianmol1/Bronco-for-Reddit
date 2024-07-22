package com.anmolsahi.domain.delegate

interface SavedPostDelegate {
    suspend fun togglePostSavedStatus(postId: String): Boolean
}
