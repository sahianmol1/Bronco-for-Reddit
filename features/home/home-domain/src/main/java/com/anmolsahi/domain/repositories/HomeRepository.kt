package com.anmolsahi.domain.repositories

import com.anmolsahi.domain.models.RedditPost
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHotPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String? = null,
    ): Flow<List<RedditPost>?>

    fun getTopPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String? = null,
    ): Flow<List<RedditPost>?>

    fun getNewPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String? = null,
    ): Flow<List<RedditPost>?>

    fun getBestPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String? = null,
    ): Flow<List<RedditPost>?>

    fun getRisingPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String? = null,
    ): Flow<List<RedditPost>?>

    fun getControversialPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String? = null,
    ): Flow<List<RedditPost>?>

    suspend fun togglePostSavedStatus(postId: String): Boolean

    suspend fun getPostById(postId: String): RedditPost?
}
