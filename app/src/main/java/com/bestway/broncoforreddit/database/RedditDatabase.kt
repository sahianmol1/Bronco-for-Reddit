package com.bestway.broncoforreddit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestway.data.local.entity.RedditPostDao
import com.bestway.data.local.entity.RedditPostEntity

@Database(entities = [RedditPostEntity::class], version = 1)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun getRedditPostDao(): RedditPostDao
}