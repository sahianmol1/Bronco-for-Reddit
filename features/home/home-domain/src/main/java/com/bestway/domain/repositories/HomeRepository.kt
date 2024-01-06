package com.bestway.domain.repositories

import com.bestway.models.listings.ListingsResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getHotPosts(): Flow<ListingsResponse>

    fun getTopPosts(): Flow<ListingsResponse>

    fun getNewPosts(): Flow<ListingsResponse>

    fun getBestPosts(): Flow<ListingsResponse>

    fun getRisingPosts(): Flow<ListingsResponse>

    fun getControversialPosts(): Flow<ListingsResponse>
}
