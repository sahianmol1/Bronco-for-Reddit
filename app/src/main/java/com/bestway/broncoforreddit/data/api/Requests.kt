package com.bestway.broncoforreddit.data.api

import com.bestway.broncoforreddit.data.models.ListingsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun getHotListings(): ListingsResponse {
    return client.get("https://www.reddit.com/hot.json").body()
}
