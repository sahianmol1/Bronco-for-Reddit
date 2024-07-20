package com.bestway.data.remote

import com.bestway.data.apihelper.getSafeResponse
import com.bestway.data.model.remote.ListingsResponse
import com.bestway.data.remote.utils.EndPoints
import io.ktor.client.HttpClient

class HomeService(private val client: HttpClient) {
    suspend fun getHotListings(nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.HOT + "?after=$nextPageKey&raw_json=1")
    }

    suspend fun getNewListings(nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.NEW + "?after=$nextPageKey&raw_json=1")
    }

    suspend fun getTopListings(nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.TOP + "?after=$nextPageKey&raw_json=1")
    }

    suspend fun getBestListings(nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.BEST + "?after=$nextPageKey&raw_json=1")
    }

    suspend fun getRisingListings(nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.RISING + "?after=$nextPageKey&raw_json=1")
    }

    suspend fun getControversialListings(nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.CONTROVERSIAL + "?after=$nextPageKey&raw_json=1")
    }
}
