package com.bestway.data.repositories.homerepository

import com.bestway.data.local.entity.RedditPostDao
import com.bestway.data.model.asDomain
import com.bestway.data.model.asEntity
import com.bestway.data.remote.api.HomeService
import com.bestway.domain.model.RedditPost
import com.bestway.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val redditPostDao: RedditPostDao,
    private val homeService: HomeService
) : HomeRepository {
    override fun getHotPosts(): Flow<List<RedditPost>?> {
        return flow {
            emit(
                if (redditPostDao.getAllRedditPosts().isNotEmpty()) {
                    redditPostDao.getAllRedditPosts().asDomain()
                } else {
                    val listings = homeService.getControversialListings().getOrThrow().asEntity()
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }
                    redditPostDao.getAllRedditPosts().asDomain()
                }
            )
        }
    }

    override fun getTopPosts(): Flow<List<RedditPost>?> {
        return flow {
            emit(
                if (redditPostDao.getAllRedditPosts().isNotEmpty()) {
                    redditPostDao.getAllRedditPosts().asDomain()
                } else {
                    val listings = homeService.getControversialListings().getOrThrow().asEntity()
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }
                    redditPostDao.getAllRedditPosts().asDomain()
                }
            )
        }
    }

    override fun getNewPosts(): Flow<List<RedditPost>?> {
        return flow {
            emit(
                if (redditPostDao.getAllRedditPosts().isNotEmpty()) {
                    redditPostDao.getAllRedditPosts().asDomain()
                } else {
                    val listings = homeService.getControversialListings().getOrThrow().asEntity()
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }
                    redditPostDao.getAllRedditPosts().asDomain()
                }
            )
        }
    }

    override fun getBestPosts(): Flow<List<RedditPost>?> {
        return flow {
            emit(
                if (redditPostDao.getAllRedditPosts().isNotEmpty()) {
                    redditPostDao.getAllRedditPosts().asDomain()
                } else {
                    val listings = homeService.getControversialListings().getOrThrow().asEntity()
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }
                    redditPostDao.getAllRedditPosts().asDomain()
                }
            )
        }
    }

    override fun getRisingPosts(): Flow<List<RedditPost>?> {
        return flow {
            emit(
                if (redditPostDao.getAllRedditPosts().isNotEmpty()) {
                    redditPostDao.getAllRedditPosts().asDomain()
                } else {
                    val listings = homeService.getControversialListings().getOrThrow().asEntity()
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }
                    redditPostDao.getAllRedditPosts().asDomain()
                }
            )
        }
    }

    override fun getControversialPosts(): Flow<List<RedditPost>?> {
        return flow {
            emit(
                if (redditPostDao.getAllRedditPosts().isNotEmpty()) {
                    redditPostDao.getAllRedditPosts().asDomain()
                } else {
                    val listings = homeService.getControversialListings().getOrThrow().asEntity()
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }
                    redditPostDao.getAllRedditPosts().asDomain()
                }
            )
        }
    }
}
