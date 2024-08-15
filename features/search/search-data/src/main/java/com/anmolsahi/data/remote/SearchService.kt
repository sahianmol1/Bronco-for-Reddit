package com.anmolsahi.data.remote

import com.anmolsahi.data.apihelper.getSafeResponse
import com.anmolsahi.data.model.remote.ListingsResponse
import io.ktor.client.HttpClient

internal class SearchService(
    private val client: HttpClient,
) {
    suspend fun searchReddit(
        query: String,
        nextPageKey: String? = null,
    ): Result<ListingsResponse> {
        return client.getSafeResponse("${EndPoints.SEARCH}?q=$query&after=$nextPageKey&raw_json=1")
    }
}
