package com.anmolsahi.data.repositories

import android.util.Log
import com.anmolsahi.data.local.SavedPostDao
import com.anmolsahi.data.mappers.asDomain
import com.anmolsahi.data.mappers.fromDomain
import com.anmolsahi.domain.model.SavedPost
import com.anmolsahi.domain.repositories.SavedPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SavedPostRepositoryImpl(
    private val dao: SavedPostDao,
) : SavedPostRepository {

    private companion object {
        const val TAG = "SavedPostRepositoryImpl"
    }

    override fun getAllSavedPosts(): Flow<List<SavedPost>> =
        dao.getAllSavedPosts().map { data -> data.asDomain() }

    override suspend fun insertPost(post: SavedPost) {
        dao.insertPost(post.fromDomain())
    }

    override suspend fun getSavedPostById(id: String): SavedPost? {
        return try {
            dao.getSavedPostById(id)?.asDomain()
        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    override suspend fun deleteSavedPost(id: String) = dao.deleteSavedPost(id)

    override suspend fun togglePostSavedStatusInDb(post: SavedPost?): Boolean {
        post?.let {
            val postFromDb = getSavedPostById(post.id)
            return if (postFromDb != null) {
                deleteSavedPost(postFromDb.id)
                false
            } else {
                insertPost(post)
                true
            }
        } ?: return false
    }
}
