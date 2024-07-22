package com.anmolsahi.postdetailsdata.remote

import com.anmolsahi.data.apihelper.ApiConstants.BASE_URL
import com.anmolsahi.data.apihelper.getSafeResponse
import com.anmolsahi.data.model.remote.ListingsResponse
import io.ktor.client.HttpClient

class PostDetailsService(
    private val client: HttpClient,
) {
    suspend fun getPost(postUrl: String): Result<List<ListingsResponse>> {
        return client.getSafeResponse("$BASE_URL$postUrl.json")
    }
}
