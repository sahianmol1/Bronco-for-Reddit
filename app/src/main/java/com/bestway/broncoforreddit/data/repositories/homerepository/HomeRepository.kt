package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.models.ListingsResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getHotPosts(): Flow<ListingsResponse>

    suspend fun getTopPosts(): Flow<ListingsResponse>

    suspend fun getNewPosts(): Flow<ListingsResponse>

    suspend fun getBestPosts(): Flow<ListingsResponse>

    suspend fun getRisingPosts(): Flow<ListingsResponse>

    suspend fun getControversialPosts(): Flow<ListingsResponse>
}