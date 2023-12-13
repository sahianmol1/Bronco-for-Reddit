package com.bestway.broncoforreddit.data.repositories.homerepository

import com.bestway.broncoforreddit.data.local.dao.HotPostsDao
import com.bestway.broncoforreddit.data.local.models.HotPost
import com.bestway.broncoforreddit.data.remote.api.ApiRequests
import com.bestway.broncoforreddit.data.remote.models.ListingsResponse
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl
@Inject
constructor(private val apiRequests: ApiRequests, private val hotPostsDao: HotPostsDao) :
    HomeRepository {
    override suspend fun getHotPosts(): Flow<List<HotPost>> {
        val response = apiRequests.getHotListings().getOrNull()

         hotPostsDao.insertAllPosts(
            response
                ?.data
                ?.children
                ?.map {
                    HotPost(
                        postId = it.childrenData.id,
                        subName = it.childrenData.subName,
                        description = it.childrenData.title,
                        upVotes = it.childrenData.upVotes,
                        comments = it.childrenData.comments,
                        imageUrl = it.childrenData.imageUrl,
                        title = it.childrenData.title,
                        postUrl = it.childrenData.postUrl,
                        videoUrl = it.childrenData.secureMedia?.redditVideo?.videoUrl,
                        gifUrl = it.childrenData.gifUrl?.gifPreview?.url,
                        before = response.data.before,
                        after = response.data.after
                    )
                }
                .orEmpty()
        )

        return hotPostsDao.getAllPosts()
    }

    override suspend fun getTopPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getTopListings().getOrThrow()) }
    }

    override suspend fun getNewPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getNewListings().getOrThrow()) }
    }

    override suspend fun getBestPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getBestListings().getOrThrow()) }
    }

    override suspend fun getRisingPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getRisingListings().getOrThrow()) }
    }

    override suspend fun getControversialPosts(): Flow<ListingsResponse> {
        return flow { emit(apiRequests.getControversialListings().getOrThrow()) }
    }
}
