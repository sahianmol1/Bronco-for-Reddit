package com.anmolsahi.data.repositories.homerepository

import com.anmolsahi.data.local.dao.BestPostDao
import com.anmolsahi.data.local.dao.ControversialPostDao
import com.anmolsahi.data.local.dao.HotPostDao
import com.anmolsahi.data.local.dao.NewPostDao
import com.anmolsahi.data.local.dao.RisingPostDao
import com.anmolsahi.data.local.dao.TopPostDao
import com.anmolsahi.data.mappers.asBestPostEntity
import com.anmolsahi.data.mappers.asControversialPostEntity
import com.anmolsahi.data.mappers.asDomain
import com.anmolsahi.data.mappers.asHotPostEntity
import com.anmolsahi.data.mappers.asNewPostEntity
import com.anmolsahi.data.mappers.asRisingPostEntity
import com.anmolsahi.data.mappers.asTopPostEntity
import com.anmolsahi.data.remote.HomeService
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val hotPostDao: HotPostDao,
    private val topPostDao: TopPostDao,
    private val bestPostDao: BestPostDao,
    private val risingPostDao: RisingPostDao,
    private val controversialPostDao: ControversialPostDao,
    private val newPostDao: NewPostDao,
    private val homeService: HomeService,
) : HomeRepository {
    override fun getHotPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = hotPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getHotListings(nextPageKey = nextPageKey).getOrThrow()
                            .asHotPostEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed,
                        // delete them before inserting new ones.
                        hotPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        hotPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    hotPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getTopPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = topPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getTopListings(nextPageKey = nextPageKey).getOrThrow()
                            .asTopPostEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed,
                        // delete them before inserting new ones.
                        topPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        topPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    topPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getNewPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = newPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getNewListings(nextPageKey = nextPageKey).getOrThrow()
                            .asNewPostEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed,
                        // delete them before inserting new ones.
                        newPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        newPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    newPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getBestPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = bestPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getBestListings(nextPageKey = nextPageKey).getOrThrow()
                            .asBestPostEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed,
                        // delete them before inserting new ones.
                        bestPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        bestPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    bestPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getRisingPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = risingPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getRisingListings(nextPageKey = nextPageKey).getOrThrow()
                            .asRisingPostEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed,
                        // delete them before inserting new ones.
                        risingPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        risingPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    risingPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override fun getControversialPosts(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Flow<List<RedditPost>?> {
        return flow {
            val allPostsFromDb = controversialPostDao.getAllRedditPosts()

            emit(
                if (shouldReturnDataFromCache(allPostsFromDb, shouldRefreshData, nextPageKey)) {
                    // If there are posts in the database and no refresh is needed, emit them directly.
                    allPostsFromDb.asDomain()
                } else {
                    // If a refresh is needed or there are no posts in the database, fetch new data.
                    val listings =
                        homeService.getControversialListings(nextPageKey = nextPageKey).getOrThrow()
                            .asControversialPostEntity()

                    if (allPostsFromDb.isNotEmpty() && shouldRefreshData) {
                        // If there are existing posts in the database and refresh is needed,
                        // delete them before inserting new ones.
                        controversialPostDao.deleteAllRedditPosts()
                    }

                    // Insert the new posts into the database.
                    listings.forEach {
                        controversialPostDao.insertRedditPost(it)
                    }

                    // Fetch and emit the updated posts from the database.
                    controversialPostDao.getAllRedditPosts().asDomain()
                },
            )
        }
    }

    override suspend fun togglePostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = hotPostDao.getRedditPostById(id = postId)
            hotPostDao.insertRedditPost(
                hotPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            hotPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun getPostById(postId: String): RedditPost? {
        return try {
            hotPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            null
        }
    }

    private fun <T> shouldReturnDataFromCache(
        allPostsFromDb: List<T>,
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Boolean {
        return allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()
    }
}
