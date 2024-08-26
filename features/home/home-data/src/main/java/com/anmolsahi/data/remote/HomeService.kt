package com.anmolsahi.data.remote

import com.anmolsahi.data.model.remote.ListingsResponse
import com.anmolsahi.data.remote.utils.EndPoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class HomeService(private val client: HttpClient) {
    suspend fun getHotListings(nextPageKey: String? = null): ListingsResponse {
        return client.get(EndPoints.HOT + "?after=$nextPageKey&raw_json=1").body()
    }

    suspend fun getNewListings(nextPageKey: String? = null): ListingsResponse {
        return client.get(EndPoints.NEW + "?after=$nextPageKey&raw_json=1").body()
    }

    suspend fun getTopListings(nextPageKey: String? = null): ListingsResponse {
        return client.get(EndPoints.TOP + "?after=$nextPageKey&raw_json=1").body()
    }

    suspend fun getBestListings(nextPageKey: String? = null): ListingsResponse {
        return client.get(EndPoints.BEST + "?after=$nextPageKey&raw_json=1").body()
    }

    suspend fun getRisingListings(nextPageKey: String? = null): ListingsResponse {
        return client.get(EndPoints.RISING + "?after=$nextPageKey&raw_json=1").body()
    }

    suspend fun getControversialListings(nextPageKey: String? = null): ListingsResponse {
        return client.get(EndPoints.CONTROVERSIAL + "?after=$nextPageKey&raw_json=1").body()
    }
}
