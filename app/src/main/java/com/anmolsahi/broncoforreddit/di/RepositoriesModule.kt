package com.anmolsahi.broncoforreddit.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.anmolsahi.data.SearchRepositoryImpl
import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.local.SavedPostDao
import com.anmolsahi.data.local.dao.BestPostDao
import com.anmolsahi.data.local.dao.ControversialPostDao
import com.anmolsahi.data.local.dao.HotPostDao
import com.anmolsahi.data.local.dao.NewPostDao
import com.anmolsahi.data.local.dao.RisingPostDao
import com.anmolsahi.data.local.dao.TopPostDao
import com.anmolsahi.data.remote.HomeService
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.data.repositories.SavedPostRepositoryImpl
import com.anmolsahi.data.repositories.homerepository.HomeRepositoryImpl
import com.anmolsahi.data.repository.AppPreferencesRepositoryImpl
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.domain.repository.AppPreferencesRepository
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
        hotPostDao: HotPostDao,
        topPostDao: TopPostDao,
        bestPostDao: BestPostDao,
        risingPostDao: RisingPostDao,
        controversialPostDao: ControversialPostDao,
        newPostDao: NewPostDao,
        appPreferencesRepository: AppPreferencesRepository,
    ): HomeRepository = HomeRepositoryImpl(
        hotPostDao = hotPostDao,
        homeService = homeService,
        topPostDao = topPostDao,
        bestPostDao = bestPostDao,
        risingPostDao = risingPostDao,
        controversialPostDao = controversialPostDao,
        newPostDao = newPostDao,
        prefs = appPreferencesRepository,
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

    @Singleton
    @Provides
    fun providePreferencesDataStoreRepository(
        dataStore: DataStore<Preferences>,
    ): AppPreferencesRepository = AppPreferencesRepositoryImpl(dataStore = dataStore)
}
