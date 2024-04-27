package com.bestway.broncoforreddit.delegates

import com.anmolsahi.common_ui.delegate.CommonUiDelegate
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.bestway.domain.repositories.HomeRepository
import com.bestway.presentation.utils.asUiModel
import javax.inject.Inject

class CommonUiModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
) : CommonUiDelegate {
    override suspend fun getPostById(postId: String): RedditPostUiModel {
        return homeRepository.getPostById(postId).asUiModel()
    }

    override suspend fun updatePost(postId: String): Boolean {
        return homeRepository.updatePost(postId)
    }
}