package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.repositories.HomeRepository
import javax.inject.Inject

class SavedPostModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
) : SavedPostDelegate {
    override suspend fun togglePostSavedStatus(postId: String): Boolean {
        return homeRepository.togglePostSavedStatus(postId)
    }
}
