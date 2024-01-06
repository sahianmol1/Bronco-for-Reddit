package com.bestway.data.repositories.homerepository

import com.bestway.data.remote.api.ApiRequests
import com.bestway.domain.repositories.HomeRepository
import com.bestway.models.listings.ListingsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(private val apiRequests: ApiRequests) : HomeRepository {
    override fun getHotPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getHotListings().getOrThrow()) }
    }

    override fun getTopPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getTopListings().getOrThrow()) }
    }

    override fun getNewPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getNewListings().getOrThrow()) }
    }

    override fun getBestPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getBestListings().getOrThrow()) }
    }

    override fun getRisingPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getRisingListings().getOrThrow()) }
    }

    override fun getControversialPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getControversialListings().getOrThrow()) }
    }
}
