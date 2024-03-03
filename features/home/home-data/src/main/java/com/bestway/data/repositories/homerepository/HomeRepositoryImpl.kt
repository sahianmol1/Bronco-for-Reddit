package com.bestway.data.repositories.homerepository

import com.bestway.data.model.asDomain
import com.bestway.data.remote.api.HomeService
import com.bestway.domain.model.RedditPost
import com.bestway.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(private val homeService: HomeService) : HomeRepository {
    override fun getHotPosts(): Flow<List<RedditPost>?> {
        return flow { emit(homeService.getHotListings().getOrThrow().asDomain()) }
    }

    override fun getTopPosts(): Flow<List<RedditPost>?> {
        return flow { emit(homeService.getTopListings().getOrThrow().asDomain()) }
    }

    override fun getNewPosts(): Flow<List<RedditPost>?> {
        return flow { emit(homeService.getNewListings().getOrThrow().asDomain()) }
    }

    override fun getBestPosts(): Flow<List<RedditPost>?> {
        return flow { emit(homeService.getBestListings().getOrThrow().asDomain()) }
    }

    override fun getRisingPosts(): Flow<List<RedditPost>?> {
        return flow { emit(homeService.getRisingListings().getOrThrow().asDomain()) }
    }

    override fun getControversialPosts(): Flow<List<RedditPost>?> {
        return flow { emit(homeService.getControversialListings().getOrThrow().asDomain()) }
    }
}
