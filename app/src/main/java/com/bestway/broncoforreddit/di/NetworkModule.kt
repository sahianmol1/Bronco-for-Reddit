package com.bestway.broncoforreddit.di

import com.bestway.data.remote.api.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            engine {
                connectTimeout = Constants.CONNECTION_TIMEOUT_MILLIS
                socketTimeout = Constants.SOCKET_TIMEOUT_MILLIS
            }
        }
    }

    @Provides
    @Singleton
    fun providesHomeApiRequests(client: HttpClient) = HomeService(client)
}