package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.models.ListingsResponse
import com.bestway.broncoforreddit.data.remote.api.ApiRequests
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiRequests: ApiRequests
) : HomeRepository {
    override suspend fun getTrendingPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getHotListings())
        }
    }

    override suspend fun getTopPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getTopListings())
        }
    }

    override suspend fun getNewPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getNewListings())
        }
    }

    override suspend fun getBestPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getBestListings())
        }
    }

    override suspend fun getRisingPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getRisingListings())
        }
    }

    override suspend fun getControversialPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getControversialListings())
        }
    }

}