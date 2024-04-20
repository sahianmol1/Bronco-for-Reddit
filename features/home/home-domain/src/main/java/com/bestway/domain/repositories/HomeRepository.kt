package com.bestway.domain.repositories

import com.bestway.domain.model.RedditPost
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getHotPosts(shouldRefreshData: Boolean, nextPageKey: String? = null): Flow<List<RedditPost>?>

    fun getTopPosts(shouldRefreshData: Boolean): Flow<List<RedditPost>?>

    fun getNewPosts(shouldRefreshData: Boolean): Flow<List<RedditPost>?>

    fun getBestPosts(shouldRefreshData: Boolean): Flow<List<RedditPost>?>

    fun getRisingPosts(shouldRefreshData: Boolean): Flow<List<RedditPost>?>

    fun getControversialPosts(shouldRefreshData: Boolean): Flow<List<RedditPost>?>
}
