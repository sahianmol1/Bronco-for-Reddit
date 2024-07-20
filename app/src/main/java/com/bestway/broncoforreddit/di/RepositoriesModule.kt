package com.bestway.broncoforreddit.di

import com.bestway.data.SearchRepositoryImpl
import com.bestway.data.local.RecentSearchesDao
import com.bestway.data.local.RedditPostDao
import com.bestway.data.local.SavedPostDao
import com.bestway.data.remote.HomeService
import com.bestway.data.remote.SearchService
import com.bestway.data.repositories.SavedPostRepositoryImpl
import com.bestway.data.repositories.homerepository.HomeRepositoryImpl
import com.bestway.domain.repositories.HomeRepository
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.domain.repositories.SearchRepository
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

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchesDao: RecentSearchesDao,
        searchService: SearchService,
    ): SearchRepository =
        SearchRepositoryImpl(
            dao = searchesDao,
            service = searchService,
        )
}
