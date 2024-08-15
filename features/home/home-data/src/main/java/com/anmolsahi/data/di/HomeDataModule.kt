package com.anmolsahi.data.di

import com.anmolsahi.data.local.dao.BestPostDao
import com.anmolsahi.data.local.dao.ControversialPostDao
import com.anmolsahi.data.local.dao.HotPostDao
import com.anmolsahi.data.local.dao.NewPostDao
import com.anmolsahi.data.local.dao.RisingPostDao
import com.anmolsahi.data.local.dao.TopPostDao
import com.anmolsahi.data.remote.HomeService
import com.anmolsahi.data.repositories.homerepository.HomeRepositoryImpl
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repository.AppPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HomeDataModule {

    @Provides
    @Singleton
    fun providesHomeApiRequests(client: HttpClient) = HomeService(client)

    @Singleton
    @Provides
    @SuppressWarnings("LongParameterList")
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
}
