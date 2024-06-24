package com.bestway.broncoforreddit.delegates

import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.postdetailspresentation.postdetails.delegate.PostDetailsDelegate
import com.bestway.domain.repositories.HomeRepository
import com.bestway.presentation.utils.asUiModel
import javax.inject.Inject

class PostDetailsModuleManager @Inject constructor(
    private val homeRepository: HomeRepository,
) : PostDetailsDelegate {
    override suspend fun getPostById(postId: String): RedditPostUiModel {
        return homeRepository.getPostById(postId).asUiModel()
    }

    override suspend fun updatePost(postId: String): Boolean {
        return homeRepository.updatePost(postId)
    }
}