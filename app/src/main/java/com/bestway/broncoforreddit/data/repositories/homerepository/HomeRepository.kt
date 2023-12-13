package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.local.models.HotPost
import com.bestway.broncoforreddit.data.remote.models.ListingsResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getHotPosts(): Flow<List<HotPost>>

    suspend fun getTopPosts(): Flow<ListingsResponse>

    suspend fun getNewPosts(): Flow<ListingsResponse>

    suspend fun getBestPosts(): Flow<ListingsResponse>

    suspend fun getRisingPosts(): Flow<ListingsResponse>

    suspend fun getControversialPosts(): Flow<ListingsResponse>
}