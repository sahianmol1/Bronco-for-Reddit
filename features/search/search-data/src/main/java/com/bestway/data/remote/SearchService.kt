package com.bestway.data.remote

import com.bestway.data.apihelper.getSafeResponse
import com.bestway.data.model.remote.ListingsResponse
import io.ktor.client.HttpClient

class SearchService(
    private val client: HttpClient,
) {
    suspend fun searchReddit(query: String, nextPageKey: String? = null): Result<ListingsResponse> {
        return client.getSafeResponse("${EndPoints.SEARCH}?q=$query&after=$nextPageKey&raw_json=1")
    }
}
