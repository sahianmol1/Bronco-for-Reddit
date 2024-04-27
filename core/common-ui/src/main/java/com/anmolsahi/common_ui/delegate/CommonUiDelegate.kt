package com.anmolsahi.common_ui.delegate

import com.anmolsahi.common_ui.models.RedditPostUiModel

interface CommonUiDelegate {
    suspend fun getPostById(postId: String): RedditPostUiModel

    suspend fun updatePost(postId: String): Boolean
}