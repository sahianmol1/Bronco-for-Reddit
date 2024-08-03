package com.anmolsahi.domain.usecase

import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.utils.PostType

class TogglePostSavedStatusUseCase(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(postId: String, postType: PostType): Boolean {
        return when (postType) {
            PostType.HOT -> homeRepository.toggleHotPostSavedStatus(postId)

            PostType.NEW -> homeRepository.toggleNewPostSavedStatus(postId)

            PostType.TOP -> homeRepository.toggleTopPostSavedStatus(postId)

            PostType.BEST -> homeRepository.toggleBestPostSavedStatus(postId)

            PostType.RISING -> homeRepository.toggleRisingPostSavedStatus(postId)

            PostType.CONTROVERSIAL -> homeRepository.toggleControversialPostSavedStatus(postId)
        }
    }
}
