package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.remote.api.ApiRequests
import com.bestway.broncoforreddit.data.repositories.models.ListingsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiRequests: ApiRequests
) : HomeRepository {
    override suspend fun getTendingPosts(): Flow<ListingsResponse> {
        return flow {
            emit(apiRequests.getHotListings())
        }
    }

    override suspend fun getTopPosts() {
        TODO("Not yet implemented")
    }

    override suspend fun getNewPosts() {
        TODO("Not yet implemented")
    }

    override suspend fun getBestPosts() {
        TODO("Not yet implemented")
    }

    override suspend fun getRisingPosts() {
        TODO("Not yet implemented")
    }

    override suspend fun getControversialPosts() {
        TODO("Not yet implemented")
    }
}