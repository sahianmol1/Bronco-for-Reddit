package com.bestway.domain.repositories

import com.bestway.domain.model.RedditPost
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getHotPosts(): Flow<List<RedditPost>?>

    fun getTopPosts(): Flow<List<RedditPost>?>

    fun getNewPosts(): Flow<List<RedditPost>?>

    fun getBestPosts(): Flow<List<RedditPost>?>

    fun getRisingPosts(): Flow<List<RedditPost>?>

    fun getControversialPosts(): Flow<List<RedditPost>?>
}
