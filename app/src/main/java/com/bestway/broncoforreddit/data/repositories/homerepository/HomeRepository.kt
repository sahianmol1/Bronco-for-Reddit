package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.repositories.models.ListingsResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getTendingPosts(): Flow<ListingsResponse>

    suspend fun getTopPosts()

    suspend fun getNewPosts()

    suspend fun getBestPosts()

    suspend fun getRisingPosts()

    suspend fun getControversialPosts()
}