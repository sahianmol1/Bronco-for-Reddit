package com.anmolsahi.data.local.dao.contract

import com.anmolsahi.data.local.dao.contract.HomePostConstants.STALE_POSTS_COUNT
import com.anmolsahi.data.local.entities.contract.IHomePostEntity

interface IHomePostDao {
    suspend fun getAllRedditPosts(): List<IHomePostEntity>

    suspend fun getLastNPosts(count: Int = STALE_POSTS_COUNT): List<String>

    suspend fun deleteStalePosts(ids: List<String>)
}

object HomePostConstants {
    const val STALE_POSTS_COUNT = 25
}
