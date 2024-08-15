package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import java.util.logging.Logger

class DeleteSavedPostUseCase(
    private val delegate: PostDetailsDelegate,
) {
    private companion object {
        const val TAG = "DeleteSavedPostUseCase"
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    suspend operator fun invoke(postId: String) {
        try {
            delegate.deleteSavedPost(postId)
            delegate.togglePostSavedStatus(postId)
        } catch (e: Exception) {
            Logger.getLogger(TAG).severe(e.message.toString())
        }
    }
}
