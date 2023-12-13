package com.bestway.broncoforreddit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestway.broncoforreddit.data.local.dao.HotPostsDao
import com.bestway.broncoforreddit.data.local.models.HotPost

@Database(entities = [HotPost::class], version = 1)
abstract class BroncoDatabase: RoomDatabase() {
    abstract val hotPostsDao: HotPostsDao
}