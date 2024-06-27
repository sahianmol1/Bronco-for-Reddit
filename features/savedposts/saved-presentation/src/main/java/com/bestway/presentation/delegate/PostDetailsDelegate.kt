package com.anmolsahi.postdetailspresentation.postdetails.delegate

interface SavedPostDelegate {
    suspend fun togglePostSavedStatus(postId: String): Boolean
}