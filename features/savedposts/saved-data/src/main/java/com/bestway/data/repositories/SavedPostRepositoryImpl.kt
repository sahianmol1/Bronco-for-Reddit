package com.bestway.data.repositories

import com.bestway.data.local.SavedPostDao
import com.bestway.data.mappers.asDomain
import com.bestway.data.mappers.fromDomain
import com.bestway.domain.model.SavedPost
import com.bestway.domain.repositories.SavedPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SavedPostRepositoryImpl(
    private val dao: SavedPostDao,
) : SavedPostRepository {
    override fun getAllSavedPosts(): Flow<List<SavedPost>> =
        flow {
            emit(dao.getAllSavedPosts().asDomain().reversed())
        }

    override suspend fun insertPost(post: SavedPost) {
        dao.insertPost(post.fromDomain())
    }

    override suspend fun getSavedPostById(id: String): SavedPost? = dao.getSavedPostById(id = id)?.asDomain()

    override suspend fun deleteSavedPost(id: String) = dao.deleteSavedPost(id)
}
