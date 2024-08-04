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

    suspend fun toggleHotPostSavedStatus(postId: String): Boolean

    suspend fun toggleTopPostSavedStatus(postId: String): Boolean

    suspend fun toggleNewPostSavedStatus(postId: String): Boolean

    suspend fun toggleBestPostSavedStatus(postId: String): Boolean

    suspend fun toggleRisingPostSavedStatus(postId: String): Boolean

    suspend fun toggleControversialPostSavedStatus(postId: String): Boolean

    suspend fun getHotPostById(postId: String): RedditPost?

    suspend fun getTopPostById(postId: String): RedditPost?

    suspend fun getNewPostById(postId: String): RedditPost?

    suspend fun getBestPostById(postId: String): RedditPost?

    suspend fun getRisingPostById(postId: String): RedditPost?

    suspend fun getControversialPostById(postId: String): RedditPost?

    suspend fun deleteStalePosts()
}
