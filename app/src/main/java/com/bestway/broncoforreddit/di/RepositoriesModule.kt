package com.bestway.broncoforreddit.di

import com.bestway.data.local.SavedPostDao
import com.bestway.data.local.entity.RedditPostDao
import com.bestway.data.remote.api.HomeService
import com.bestway.data.repositories.SavedPostRepositoryImpl
import com.bestway.data.repositories.homerepository.HomeRepositoryImpl
import com.bestway.domain.repositories.HomeRepository
import com.bestway.domain.repositories.SavedPostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton
    @Provides
    fun provideHomeRepository(
        homeService: HomeService,
        redditPostDao: RedditPostDao,
    ): HomeRepository =
        HomeRepositoryImpl(
            redditPostDao = redditPostDao,
            homeService = homeService,
        )

    @Singleton
    @Provides
    fun provideSavedPostsRepository(savedPostsDao: SavedPostDao): SavedPostRepository =
        SavedPostRepositoryImpl(
            dao = savedPostsDao,
        )
}
