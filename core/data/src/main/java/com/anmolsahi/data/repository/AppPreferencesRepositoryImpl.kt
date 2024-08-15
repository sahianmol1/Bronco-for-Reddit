package com.anmolsahi.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.anmolsahi.data.datastore.PreferencesKeys
import com.anmolsahi.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SuppressWarnings("TooManyFunctions")
class AppPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : AppPreferencesRepository {
    override suspend fun getHotPostsTimestamp(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.hotPostsKey] ?: 0L
        }
    }

    override suspend fun getTopPostsTimestamp(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.topPostsKey] ?: 0L
        }
    }

    override suspend fun getRisingPostsTimestamp(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.risingPostsKey] ?: 0L
        }
    }

    override suspend fun getBestPostsTimestamp(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.bestPostsKey] ?: 0L
        }
    }

    override suspend fun getControversialPostsTimestamp(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.controversialPostsKey] ?: 0L
        }
    }

    override suspend fun getNewPostsTimestamp(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.newPostsKey] ?: 0L
        }
    }

    override suspend fun saveHotPostsTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.hotPostsKey] = timestamp
        }
    }

    override suspend fun saveTopPostsTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.topPostsKey] = timestamp
        }
    }

    override suspend fun saveRisingPostsTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.risingPostsKey] = timestamp
        }
    }

    override suspend fun saveBestPostsTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.bestPostsKey] = timestamp
        }
    }

    override suspend fun saveControversialPostsTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.controversialPostsKey] = timestamp
        }
    }

    override suspend fun saveNewPostsTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.newPostsKey] = timestamp
        }
    }
}
