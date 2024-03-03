package com.bestway.broncoforreddit.di

import com.bestway.data.remote.api.HomeService
import com.bestway.data.repositories.homerepository.HomeRepositoryImpl
import com.bestway.domain.repositories.HomeRepository
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
    fun provideHomeRepository(homeService: HomeService): HomeRepository {
        return HomeRepositoryImpl(
            homeService = homeService
        )
    }
}