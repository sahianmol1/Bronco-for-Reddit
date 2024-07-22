package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate

class DeleteSavedPostUseCase(
    private val delegate: PostDetailsDelegate,
) {
    suspend operator fun invoke(postId: String) {
        try {
            delegate.deleteSavedPost(postId)
            delegate.togglePostSavedStatus(postId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
