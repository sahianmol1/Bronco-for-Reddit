package com.anmolsahi.broncoforreddit.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.datadog.android.okhttp.DatadogEventListener
import com.datadog.android.okhttp.DatadogInterceptor
import com.datadog.android.okhttp.trace.TracingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true
        engine {
            config {
                val tracedHosts = listOf("reddit.com")
//                connectTimeout(java.time.Duration.parse(Constants.CONNECTION_TIMEOUT_MILLIS.toString()))

                addInterceptor(DatadogInterceptor.Builder(tracedHosts).build())
                addNetworkInterceptor(TracingInterceptor.Builder(tracedHosts).build())
                eventListenerFactory(DatadogEventListener.Factory())
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    allowTrailingComma = true
                },
            )
        }

        install(HttpCache)
    }
}
