package com.anmolsahi.postdetailsdomain.usecase

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate

class DeleteSavedPostUseCase(
    private val delegate: PostDetailsDelegate,
) {
    suspend operator fun invoke(postId: String) {
        delegate.deleteSavedPost(postId)
        try {
            delegate.togglePostSavedStatus(postId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
