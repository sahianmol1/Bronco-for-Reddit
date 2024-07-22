package com.anmolsahi.data.repositories.homerepository

import com.anmolsahi.data.local.RedditPostDao
import com.anmolsahi.data.mappers.asDomain
import com.anmolsahi.data.mappers.asEntity
import com.anmolsahi.data.model.local.RedditPostEntity
import com.anmolsahi.data.remote.HomeService
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val redditPostDao: RedditPostDao,
    private val homeService: HomeService,
) : HomeRepository {
    override fun getHotPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = redditPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed, delete them before inserting new ones.
                        redditPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    redditPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getTopPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = redditPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed, delete them before inserting new ones.
                        redditPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    redditPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getNewPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = redditPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed, delete them before inserting new ones.
                        redditPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    redditPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getBestPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = redditPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed, delete them before inserting new ones.
                        redditPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    redditPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getRisingPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = redditPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed, delete them before inserting new ones.
                        redditPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    redditPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getControversialPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = redditPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed, delete them before inserting new ones.
                        redditPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        redditPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    redditPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override suspend fun togglePostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = redditPostDao.getRedditPostById(id = postId)
            redditPostDao.insertRedditPost(
                redditPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            redditPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun getPostById(postId: String): RedditPost? {
        return try {
            redditPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            null
        }
    }

    private fun shouldReturnDataFromCache(
        allPostsFromDb: List<RedditPostEntity>,
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Boolean {
        return allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()
    }
}
