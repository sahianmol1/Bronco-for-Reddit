package com.bestway.broncoforreddit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestway.data.local.RecentSearchEntity
import com.bestway.data.local.RecentSearchesDao
import com.bestway.data.local.SavedPostDao
import com.bestway.data.local.SavedPostEntity
import com.bestway.data.local.entity.RedditPostDao
import com.bestway.data.model.local.RedditPostEntity

@Database(
    entities = [RedditPostEntity::class, SavedPostEntity::class, RecentSearchEntity::class],
    version = 1,
)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun getRedditPostDao(): RedditPostDao

    abstract fun getSavedPostDao(): SavedPostDao

    abstract fun getRecentSearchesDao(): RecentSearchesDao
}
