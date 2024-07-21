package com.anmolsahi.postdetailsdata.remote

import com.bestway.data.apihelper.ApiConstants.BASE_URL
import com.bestway.data.apihelper.getSafeResponse
import com.bestway.data.model.remote.ListingsResponse
import io.ktor.client.HttpClient

class PostDetailsService(
    private val client: HttpClient,
) {
    suspend fun getPost(postUrl: String): Result<List<ListingsResponse>> {
        return client.getSafeResponse("$BASE_URL$postUrl.json")
    }
}
