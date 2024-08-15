package com.anmolsahi.data.di

import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.data.repositories.SearchRepositoryImpl
import com.anmolsahi.domain.repositories.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SearchPostsDataModule {

    @Provides
    @Singleton
    fun providesSearchApiRequests(client: HttpClient) = SearchService(client)

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
