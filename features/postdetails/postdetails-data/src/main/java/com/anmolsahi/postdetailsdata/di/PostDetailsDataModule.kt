package com.anmolsahi.postdetailsdata.di

import com.anmolsahi.postdetailsdata.remote.PostDetailsService
import com.anmolsahi.postdetailsdata.repositories.PostDetailsRepositoryImpl
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostDetailsDataModule {

    @Provides
    @Singleton
    fun providesPostDetailsApiRequests(client: HttpClient) = PostDetailsService(client)

    @Singleton
    @Provides
    fun providePostDetailsRepository(service: PostDetailsService): PostDetailsRepository =
        PostDetailsRepositoryImpl(service = service)
}
