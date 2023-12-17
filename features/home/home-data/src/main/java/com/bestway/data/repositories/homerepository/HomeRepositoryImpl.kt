package com.bestway.data.repositories.homerepository

import com.bestway.data.remote.api.ApiRequests
import com.bestway.domain.repositories.HomeRepository
import com.bestway.models.listings.ListingsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(private val apiRequests: ApiRequests) : HomeRepository {
    override suspend fun getHotPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getHotListings().getOrThrow()) }
    }

    override suspend fun getTopPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getTopListings().getOrThrow()) }
    }

    override suspend fun getNewPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getNewListings().getOrThrow()) }
    }

    override suspend fun getBestPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getBestListings().getOrThrow()) }
    }

    override suspend fun getRisingPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getRisingListings().getOrThrow()) }
    }

    override suspend fun getControversialPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getControversialListings().getOrThrow()) }
    }
}
