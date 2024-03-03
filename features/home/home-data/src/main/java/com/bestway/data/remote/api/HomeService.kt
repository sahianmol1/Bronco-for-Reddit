package com.bestway.data.remote.api

import com.bestway.data.model.ListingsResponse
import com.bestway.data.remote.api.utils.EndPoints
import com.bestway.data.remote.api.utils.getSafeResponse
import io.ktor.client.HttpClient


class HomeService(private val client: HttpClient) {
    suspend fun getHotListings(): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.HOT)
    }

    suspend fun getNewListings(): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.NEW)
    }

    suspend fun getTopListings(): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.TOP)
    }

    suspend fun getBestListings(): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.BEST)
    }

    suspend fun getRisingListings(): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.RISING)
    }

    suspend fun getControversialListings(): Result<ListingsResponse> {
        return client.getSafeResponse(EndPoints.CONTROVERSIAL)
    }
}


