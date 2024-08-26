package com.anmolsahi.postdetailsdata.remote

import com.anmolsahi.data.apihelper.ApiConstants.BASE_URL
import com.anmolsahi.data.model.remote.ListingsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class PostDetailsService(
    private val client: HttpClient,
) {
    suspend fun getPost(postUrl: String): List<ListingsResponse> {
        return client.get("$BASE_URL$postUrl.json?raw_json=1").body()
    }
}
