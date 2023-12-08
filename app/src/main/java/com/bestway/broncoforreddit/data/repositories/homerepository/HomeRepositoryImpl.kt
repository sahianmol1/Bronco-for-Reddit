package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.models.ListingsResponse
import com.bestway.broncoforreddit.data.remote.api.ApiRequests
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiRequests: ApiRequests
) : HomeRepository {
    override suspend fun getHotPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getHotListings().getOrThrow())
        }
    }

    override suspend fun getTopPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getTopListings().getOrThrow())
        }
    }

    override suspend fun getNewPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getNewListings().getOrThrow())
        }
    }

    override suspend fun getBestPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getBestListings().getOrThrow())
        }
    }

    override suspend fun getRisingPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getRisingListings().getOrThrow())
        }
    }

    override suspend fun getControversialPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getControversialListings().getOrThrow())
        }
    }

}
