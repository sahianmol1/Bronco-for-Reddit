package com.bestway.data.repositories.homerepository

import com.bestway.data.local.entity.RedditPostDao
import com.bestway.data.model.remote.asDomain
import com.bestway.data.model.remote.asEntity
import com.bestway.data.remote.api.HomeService
import com.bestway.domain.models.RedditPost
import com.bestway.domain.repositories.HomeRepository
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
                if (allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()) {
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
                if (allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()) {
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
                if (allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()) {
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
                if (allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()) {
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
                if (allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()) {
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
                if (allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()) {
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
        val redditPost = redditPostDao.getRedditPostById(id = postId)
        redditPostDao.insertRedditPost(redditPostEntity = redditPost.copy(isSaved = !redditPost.isSaved))
        return redditPostDao.getRedditPostById(id = postId).isSaved
    }

    override suspend fun getPostById(postId: String): RedditPost {
        return redditPostDao.getRedditPostById(id = postId).asDomain()
    }
}
