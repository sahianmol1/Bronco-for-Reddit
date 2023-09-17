package com.bestway.broncoforreddit.data.remote.api

import com.bestway.broncoforreddit.data.repositories.models.ListingsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ApiRequests @Inject constructor(private val client: HttpClient) {
    suspend fun getHotListings(): ListingsResponse {
        return client.get("https://www.reddit.com/hot.json").body()
    }
}


