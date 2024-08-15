package com.anmolsahi.domain.usecase

import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.repositories.SavedPostRepository
import java.util.logging.Logger

class DeleteSavedPostUseCase(
    private val repository: SavedPostRepository,
    private val delegate: SavedPostDelegate,
) {
    private companion object {
        const val TAG = "DeleteSavedPostUseCase"
    }

    suspend operator fun invoke(postId: String) {
        try {
            repository.deleteSavedPost(postId)
            delegate.togglePostSavedStatus(postId)
        } catch (e: Exception) {
            Logger.getLogger(TAG).severe(e.message.toString())
        }
    }
}
