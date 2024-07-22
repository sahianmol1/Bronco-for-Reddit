package com.anmolsahi.broncoforreddit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anmolsahi.data.local.RecentSearchEntity
import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.local.RedditPostDao
import com.anmolsahi.data.local.SavedPostDao
import com.anmolsahi.data.local.SavedPostEntity
import com.anmolsahi.data.model.local.RedditPostEntity

@Database(
    entities = [RedditPostEntity::class, SavedPostEntity::class, RecentSearchEntity::class],
    version = 1,
)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun getRedditPostDao(): RedditPostDao

    abstract fun getSavedPostDao(): SavedPostDao

    abstract fun getRecentSearchesDao(): RecentSearchesDao
}
