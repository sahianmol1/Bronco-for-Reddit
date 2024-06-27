package com.bestway.broncoforreddit.delegates

import com.bestway.domain.delegate.SavedPostDelegate
import com.bestway.domain.repositories.HomeRepository
import javax.inject.Inject

class SavedPostModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
) : SavedPostDelegate {
    override suspend fun togglePostSavedStatus(postId: String): Boolean {
        return homeRepository.togglePostSavedStatus(postId)
    }
}