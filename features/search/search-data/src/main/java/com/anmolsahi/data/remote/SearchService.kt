package com.anmolsahi.data.remote

import com.anmolsahi.data.model.remote.ListingsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class SearchService(
    private val client: HttpClient,
) {
    suspend fun searchReddit(query: String, nextPageKey: String? = null): ListingsResponse {
        return client
            .get("${EndPoints.SEARCH}?q=$query&after=$nextPageKey&raw_json=1")
            .body()
    }
}
