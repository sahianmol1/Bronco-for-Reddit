package com.bestway.broncoforreddit.data.remote.api

import com.bestway.broncoforreddit.data.models.ListingsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRequests @Inject constructor(private val client: HttpClient) {
    suspend fun getHotListings(): ListingsResponse {
        return client.get(EndPoints.HOT).body()
    }

    suspend fun getNewListings(): ListingsResponse {
        return client.get(EndPoints.NEW).body()
    }

    suspend fun getTopListings(): ListingsResponse {
        return client.get(EndPoints.TOP).body()
    }

    suspend fun getBestListings(): ListingsResponse {
        return client.get(EndPoints.BEST).body()
    }

    suspend fun getRisingListings(): ListingsResponse {
        return client.get(EndPoints.RISING).body()
    }

    suspend fun getControversialListings(): ListingsResponse {
        return client.get(EndPoints.CONTROVERSIAL).body()
    }
}


