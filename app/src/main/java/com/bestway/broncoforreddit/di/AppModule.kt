package com.bestway.broncoforreddit.di

import android.content.Context
import androidx.room.Room
import com.bestway.broncoforreddit.data.local.dao.HotPostsDao
import com.bestway.broncoforreddit.data.local.database.BroncoDatabase
import com.bestway.broncoforreddit.data.remote.api.ApiRequests
import com.bestway.broncoforreddit.data.repositories.homerepository.HomeRepository
import com.bestway.broncoforreddit.data.repositories.homerepository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }

            engine {
                connectTimeout = Constants.CONNECTION_TIMEOUT_MILLIS
                socketTimeout = Constants.SOCKET_TIMEOUT_MILLIS
            }
        }
    }

    @Singleton
    @Provides
    fun provideHomeRepository(apiRequests: ApiRequests, hotPostDao: HotPostsDao): HomeRepository {
        return HomeRepositoryImpl(apiRequests = apiRequests, hotPostsDao = hotPostDao)
    }

    @Singleton
    @Provides
    fun provideBroncoDatabase(@ApplicationContext context: Context): BroncoDatabase {
        return Room.databaseBuilder(context = context, BroncoDatabase::class.java, "bronco_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideHotPostsDao(roomDatabase: BroncoDatabase): HotPostsDao = roomDatabase.hotPostsDao
}
