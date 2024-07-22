package com.anmolsahi.broncoforreddit.di

import android.content.Context
import androidx.room.Room
import com.anmolsahi.broncoforreddit.database.RedditDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRedditDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        RedditDatabase::class.java,
        name = "reddit_db",
    ).build()

    @Provides
    @Singleton
    fun provideRedditPostDao(redditDatabase: RedditDatabase) = redditDatabase.getRedditPostDao()

    @Provides
    @Singleton
    fun provideSavedPostDao(redditDatabase: RedditDatabase) = redditDatabase.getSavedPostDao()

    @Provides
    @Singleton
    fun provideRecentSearchesDao(redditDatabase: RedditDatabase) = redditDatabase.getRecentSearchesDao()
}
