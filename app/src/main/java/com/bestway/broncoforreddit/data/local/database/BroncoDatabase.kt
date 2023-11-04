package com.bestway.broncoforreddit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestway.broncoforreddit.data.local.database.dao.HotPostsDao
import com.bestway.broncoforreddit.data.local.database.dao.HotPostsRemoteKeysDao
import com.bestway.broncoforreddit.data.models.ChildrenData
import com.bestway.broncoforreddit.data.models.HotPostsRemoteKeys

@Database(entities = [ChildrenData::class, HotPostsRemoteKeys::class], version = 1)
abstract class BroncoDatabase: RoomDatabase() {
    abstract fun getHotPostsDao(): HotPostsDao
    abstract fun getHotPostsRemoteKeysDao(): HotPostsRemoteKeysDao
}