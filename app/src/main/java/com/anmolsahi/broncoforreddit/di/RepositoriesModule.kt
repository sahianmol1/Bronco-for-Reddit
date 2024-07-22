package com.anmolsahi.broncoforreddit.di

import com.anmolsahi.data.SearchRepositoryImpl
import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.local.RedditPostDao
import com.anmolsahi.data.local.SavedPostDao
import com.anmolsahi.data.remote.HomeService
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.data.repositories.SavedPostRepositoryImpl
import com.anmolsahi.data.repositories.homerepository.HomeRepositoryImpl
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.postdetailsdata.remote.PostDetailsService
import com.anmolsahi.postdetailsdata.repositories.PostDetailsRepositoryImpl
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository
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
    ): HomeRepository = HomeRepositoryImpl(
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
    ): SearchRepository = SearchRepositoryImpl(
        dao = searchesDao,
        service = searchService,
    )

    @Singleton
    @Provides
    fun providePostDetailsRepository(service: PostDetailsService): PostDetailsRepository =
        PostDetailsRepositoryImpl(service = service)
}
