package com.anmolsahi.broncoforreddit.di

import com.anmolsahi.data.remote.HomeService
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.postdetailsdata.remote.PostDetailsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(Android) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                },
            )
        }

        install(HttpCache)

        engine {
            connectTimeout = Constants.CONNECTION_TIMEOUT_MILLIS
            socketTimeout = Constants.SOCKET_TIMEOUT_MILLIS
        }
    }

    @Provides
    @Singleton
    fun providesHomeApiRequests(client: HttpClient) = HomeService(client)

    @Provides
    @Singleton
    fun providesSearchApiRequests(client: HttpClient) = SearchService(client)

    @Provides
    @Singleton
    fun providesPostDetailsApiRequests(client: HttpClient) = PostDetailsService(client)
}
