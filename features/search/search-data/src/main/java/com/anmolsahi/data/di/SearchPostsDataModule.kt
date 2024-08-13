package com.anmolsahi.data.di

import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.data.repositories.SearchRepositoryImpl
import com.anmolsahi.domain.repositories.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchPostsDataModule {

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchesDao: RecentSearchesDao,
        searchService: SearchService,
    ): SearchRepository = SearchRepositoryImpl(
        dao = searchesDao,
        service = searchService,
    )
}
