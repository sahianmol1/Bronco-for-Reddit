package com.bestway.broncoforreddit.di

import com.bestway.broncoforreddit.data.remote.api.ApiRequests
import com.bestway.broncoforreddit.data.repositories.homerepository.HomeRepository
import com.bestway.broncoforreddit.data.repositories.homerepository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                host = Constants.BASE_URL
                url { protocol = URLProtocol.HTTPS }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            engine {
                endpoint {
                    keepAliveTime = Constants.CONNECTION_TIMEOUT_MILLIS
                    connectTimeout = Constants.CONNECTION_TIMEOUT_MILLIS
                    connectAttempts = Constants.CONNECTION_RETRIES
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideApiRequests(client: HttpClient) = ApiRequests(client = client)

    @Singleton
    @Provides
    fun provideHomeRepository(apiRequests: ApiRequests): HomeRepository {
        return HomeRepositoryImpl(apiRequests = apiRequests)
    }
}