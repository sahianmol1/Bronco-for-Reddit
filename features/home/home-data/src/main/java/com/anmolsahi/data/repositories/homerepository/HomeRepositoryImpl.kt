package com.anmolsahi.data.repositories.homerepository

import android.util.Log
import com.anmolsahi.data.local.dao.BestPostDao
import com.anmolsahi.data.local.dao.ControversialPostDao
import com.anmolsahi.data.local.dao.HotPostDao
import com.anmolsahi.data.local.dao.NewPostDao
import com.anmolsahi.data.local.dao.RisingPostDao
import com.anmolsahi.data.local.dao.TopPostDao
import com.anmolsahi.data.local.dao.contract.HomePostConstants.STALE_POSTS_COUNT
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
import com.anmolsahi.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@SuppressWarnings("LongParameterList", "TooManyFunctions", "TooGenericExceptionCaught")
internal class HomeRepositoryImpl(
    private val hotPostDao: HotPostDao,
    private val topPostDao: TopPostDao,
    private val bestPostDao: BestPostDao,
    private val risingPostDao: RisingPostDao,
    private val controversialPostDao: ControversialPostDao,
    private val newPostDao: NewPostDao,
    private val homeService: HomeService,
    private val prefs: AppPreferencesRepository,
) : HomeRepository {
    private companion object {
        const val TAG = "HomeRepositoryImpl"
    }

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
                        homeService.getHotListings(nextPageKey = nextPageKey).asHotPostEntity()

                    // After fetching the data, update the timestamp in data store preferences
                    // if the database is empty or a refresh is explicitly requested.
                    if (allPostsFromDb.isEmpty() || shouldRefreshData) {
                        prefs.saveHotPostsTimestamp(System.currentTimeMillis())
                    }

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
                        homeService.getTopListings(nextPageKey = nextPageKey).asTopPostEntity()

                    // After fetching the data, update the timestamp in data store preferences
                    // if the database is empty or a refresh is explicitly requested.
                    if (allPostsFromDb.isEmpty() || shouldRefreshData) {
                        prefs.saveTopPostsTimestamp(System.currentTimeMillis())
                    }

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
                        homeService.getNewListings(nextPageKey = nextPageKey).asNewPostEntity()

                    // After fetching the data, update the timestamp in data store preferences
                    // if the database is empty or a refresh is explicitly requested.
                    if (allPostsFromDb.isEmpty() || shouldRefreshData) {
                        prefs.saveNewPostsTimestamp(System.currentTimeMillis())
                    }

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
                        homeService.getBestListings(nextPageKey = nextPageKey).asBestPostEntity()

                    // After fetching the data, update the timestamp in data store preferences
                    // if the database is empty or a refresh is explicitly requested.
                    if (allPostsFromDb.isEmpty() || shouldRefreshData) {
                        prefs.saveBestPostsTimestamp(System.currentTimeMillis())
                    }

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
                        homeService.getRisingListings(nextPageKey = nextPageKey)
                            .asRisingPostEntity()

                    // After fetching the data, update the timestamp in data store preferences
                    // if the database is empty or a refresh is explicitly requested.
                    if (allPostsFromDb.isEmpty() || shouldRefreshData) {
                        prefs.saveRisingPostsTimestamp(System.currentTimeMillis())
                    }

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
                        homeService.getControversialListings(nextPageKey = nextPageKey)
                            .asControversialPostEntity()

                    // After fetching the data, update the timestamp in data store preferences
                    // only if the database is empty (initial call) or a refresh is explicitly requested.
                    if (allPostsFromDb.isEmpty() || shouldRefreshData) {
                        prefs.saveControversialPostsTimestamp(System.currentTimeMillis())
                    }

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

    override suspend fun toggleHotPostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = hotPostDao.getRedditPostById(id = postId)
            hotPostDao.insertRedditPost(
                hotPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            hotPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            false
        }
    }

    override suspend fun toggleTopPostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = topPostDao.getRedditPostById(id = postId)
            topPostDao.insertRedditPost(
                topPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            topPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            false
        }
    }

    override suspend fun toggleNewPostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = newPostDao.getRedditPostById(id = postId)
            newPostDao.insertRedditPost(
                newPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            newPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            false
        }
    }

    override suspend fun toggleBestPostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = bestPostDao.getRedditPostById(id = postId)
            bestPostDao.insertRedditPost(
                bestPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            bestPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            false
        }
    }

    override suspend fun toggleRisingPostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = risingPostDao.getRedditPostById(id = postId)
            risingPostDao.insertRedditPost(
                risingPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            risingPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            false
        }
    }

    override suspend fun toggleControversialPostSavedStatus(postId: String): Boolean {
        return try {
            val redditPost = controversialPostDao.getRedditPostById(id = postId)
            controversialPostDao.insertRedditPost(
                controversialPostEntity = redditPost.copy(isSaved = !redditPost.isSaved),
            )
            controversialPostDao.getRedditPostById(id = postId).isSaved
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            false
        }
    }

    override suspend fun getHotPostById(postId: String): RedditPost? {
        return try {
            hotPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun getTopPostById(postId: String): RedditPost? {
        return try {
            topPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun getNewPostById(postId: String): RedditPost? {
        return try {
            newPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun getBestPostById(postId: String): RedditPost? {
        return try {
            bestPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun getRisingPostById(postId: String): RedditPost? {
        return try {
            risingPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun getControversialPostById(postId: String): RedditPost? {
        return try {
            controversialPostDao.getRedditPostById(id = postId).asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun deleteStalePosts() {
        hotPostDao.deleteStalePosts()
        topPostDao.deleteStalePosts()
        bestPostDao.deleteStalePosts()
        newPostDao.deleteStalePosts()
        risingPostDao.deleteStalePosts()
        controversialPostDao.deleteStalePosts()
    }

    private fun <T> shouldReturnDataFromCache(
        allPostsFromDb: List<T>,
        shouldRefreshData: Boolean,
        nextPageKey: String?,
    ): Boolean {
        return allPostsFromDb.isNotEmpty() && !shouldRefreshData && nextPageKey.isNullOrEmpty()
    }

    private suspend fun HotPostDao.deleteStalePosts() {
        val totalPostsCount = getAllRedditPosts().size
        val postsToDeleteCount = when {
            totalPostsCount > 2 * STALE_POSTS_COUNT -> STALE_POSTS_COUNT
            totalPostsCount > STALE_POSTS_COUNT -> totalPostsCount - STALE_POSTS_COUNT
            else -> 0
        }

        if (postsToDeleteCount == 0) return // Early return if no posts to delete

        val idsToDelete = getLastNPostIdList(postsToDeleteCount)
        deleteStalePosts(idsToDelete)
    }

    private suspend fun TopPostDao.deleteStalePosts() {
        val totalPostsCount = getAllRedditPosts().size
        val postsToDeleteCount = when {
            totalPostsCount > 2 * STALE_POSTS_COUNT -> STALE_POSTS_COUNT
            totalPostsCount > STALE_POSTS_COUNT -> totalPostsCount - STALE_POSTS_COUNT
            else -> 0
        }

        if (postsToDeleteCount == 0) return // Early return if no posts to delete

        val idsToDelete = getLastNPostIdList(postsToDeleteCount)
        deleteStalePosts(idsToDelete)
    }

    private suspend fun BestPostDao.deleteStalePosts() {
        val totalPostsCount = getAllRedditPosts().size
        val postsToDeleteCount = when {
            totalPostsCount > 2 * STALE_POSTS_COUNT -> STALE_POSTS_COUNT
            totalPostsCount > STALE_POSTS_COUNT -> totalPostsCount - STALE_POSTS_COUNT
            else -> 0
        }

        if (postsToDeleteCount == 0) return // Early return if no posts to delete

        val idsToDelete = getLastNPostIdList(postsToDeleteCount)
        deleteStalePosts(idsToDelete)
    }

    private suspend fun NewPostDao.deleteStalePosts() {
        val totalPostsCount = getAllRedditPosts().size
        val postsToDeleteCount = when {
            totalPostsCount > 2 * STALE_POSTS_COUNT -> STALE_POSTS_COUNT
            totalPostsCount > STALE_POSTS_COUNT -> totalPostsCount - STALE_POSTS_COUNT
            else -> 0
        }

        if (postsToDeleteCount == 0) return // Early return if no posts to delete

        val idsToDelete = getLastNPostIdList(postsToDeleteCount)
        deleteStalePosts(idsToDelete)
    }

    private suspend fun RisingPostDao.deleteStalePosts() {
        val totalPostsCount = getAllRedditPosts().size
        val postsToDeleteCount = when {
            totalPostsCount > 2 * STALE_POSTS_COUNT -> STALE_POSTS_COUNT
            totalPostsCount > STALE_POSTS_COUNT -> totalPostsCount - STALE_POSTS_COUNT
            else -> 0
        }

        if (postsToDeleteCount == 0) return // Early return if no posts to delete

        val idsToDelete = getLastNPostIdList(postsToDeleteCount)
        deleteStalePosts(idsToDelete)
    }

    private suspend fun ControversialPostDao.deleteStalePosts() {
        val totalPostsCount = getAllRedditPosts().size
        val postsToDeleteCount = when {
            totalPostsCount > 2 * STALE_POSTS_COUNT -> STALE_POSTS_COUNT
            totalPostsCount > STALE_POSTS_COUNT -> totalPostsCount - STALE_POSTS_COUNT
            else -> 0
        }

        if (postsToDeleteCount == 0) return // Early return if no posts to delete

        val idsToDelete = getLastNPostIdList(postsToDeleteCount)
        deleteStalePosts(idsToDelete)
    }
}
