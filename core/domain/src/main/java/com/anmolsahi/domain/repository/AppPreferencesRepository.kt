package com.anmolsahi.domain.repository

import kotlinx.coroutines.flow.Flow

@SuppressWarnings("TooManyFunctions")
interface AppPreferencesRepository {

    suspend fun getHotPostsTimestamp(): Flow<Long>

    suspend fun getTopPostsTimestamp(): Flow<Long>

    suspend fun getRisingPostsTimestamp(): Flow<Long>

    suspend fun getBestPostsTimestamp(): Flow<Long>

    suspend fun getControversialPostsTimestamp(): Flow<Long>

    suspend fun getNewPostsTimestamp(): Flow<Long>

    suspend fun saveHotPostsTimestamp(timestamp: Long)

    suspend fun saveTopPostsTimestamp(timestamp: Long)

    suspend fun saveRisingPostsTimestamp(timestamp: Long)

    suspend fun saveBestPostsTimestamp(timestamp: Long)

    suspend fun saveControversialPostsTimestamp(timestamp: Long)

    suspend fun saveNewPostsTimestamp(timestamp: Long)
}
