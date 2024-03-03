package com.bestway.domain.repositories

import com.bestway.domain.model.RedditPosts
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getHotPosts(): Flow<List<RedditPosts>?>

    fun getTopPosts(): Flow<List<RedditPosts>?>

    fun getNewPosts(): Flow<List<RedditPosts>?>

    fun getBestPosts(): Flow<List<RedditPosts>?>

    fun getRisingPosts(): Flow<List<RedditPosts>?>

    fun getControversialPosts(): Flow<List<RedditPosts>?>
}
