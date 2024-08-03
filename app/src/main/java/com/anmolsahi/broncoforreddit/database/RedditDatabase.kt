package com.anmolsahi.broncoforreddit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anmolsahi.data.local.RecentSearchEntity
import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.local.SavedPostDao
import com.anmolsahi.data.local.SavedPostEntity
import com.anmolsahi.data.local.dao.BestPostDao
import com.anmolsahi.data.local.dao.ControversialPostDao
import com.anmolsahi.data.local.dao.HotPostDao
import com.anmolsahi.data.local.dao.NewPostDao
import com.anmolsahi.data.local.dao.RisingPostDao
import com.anmolsahi.data.local.dao.TopPostDao
import com.anmolsahi.data.local.entities.BestPostEntity
import com.anmolsahi.data.local.entities.ControversialPostEntity
import com.anmolsahi.data.local.entities.HotPostEntity
import com.anmolsahi.data.local.entities.NewPostEntity
import com.anmolsahi.data.local.entities.RisingPostEntity
import com.anmolsahi.data.local.entities.TopPostEntity

@Database(
    entities = [
        HotPostEntity::class,
        TopPostEntity::class,
        BestPostEntity::class,
        NewPostEntity::class,
        RisingPostEntity::class,
        ControversialPostEntity::class,
        SavedPostEntity::class,
        RecentSearchEntity::class,
    ],
    version = 1,
)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun getRedditPostDao(): HotPostDao

    abstract fun getTopPostDao(): TopPostDao

    abstract fun getBestPostDao(): BestPostDao

    abstract fun getNewPostDao(): NewPostDao

    abstract fun getRisingPostDao(): RisingPostDao

    abstract fun getControversialPostDao(): ControversialPostDao

    abstract fun getSavedPostDao(): SavedPostDao

    abstract fun getRecentSearchesDao(): RecentSearchesDao
}
