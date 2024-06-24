package com.anmolsahi.postdetailspresentation.postdetails.delegate

import com.anmolsahi.common_ui.models.RedditPostUiModel

interface PostDetailsDelegate {
    suspend fun getPostById(postId: String): RedditPostUiModel

    suspend fun updatePost(postId: String): Boolean
}