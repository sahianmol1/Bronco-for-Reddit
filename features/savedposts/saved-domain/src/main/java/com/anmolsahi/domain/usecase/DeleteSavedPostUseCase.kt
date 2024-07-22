package com.anmolsahi.domain.usecase

import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.repositories.SavedPostRepository

class DeleteSavedPostUseCase(
    private val repository: SavedPostRepository,
    private val delegate: SavedPostDelegate,
) {
    suspend operator fun invoke(postId: String) {
        repository.deleteSavedPost(postId)
        try {
            delegate.togglePostSavedStatus(postId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
