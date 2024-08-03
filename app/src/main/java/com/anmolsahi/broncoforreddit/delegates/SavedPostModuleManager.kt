package com.anmolsahi.broncoforreddit.delegates

import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.repositories.HomeRepository
import javax.inject.Inject

class SavedPostModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
) : SavedPostDelegate {
    override suspend fun togglePostSavedStatus(postId: String) {
        // This will try to toggle the post saved status for all the posts
        // in the home page including all tabs and handle exceptions internally
        homeRepository.run {
            toggleHotPostSavedStatus(postId)
            toggleNewPostSavedStatus(postId)
            toggleTopPostSavedStatus(postId)
            toggleBestPostSavedStatus(postId)
            toggleRisingPostSavedStatus(postId)
            toggleControversialPostSavedStatus(postId)
        }
    }
}
