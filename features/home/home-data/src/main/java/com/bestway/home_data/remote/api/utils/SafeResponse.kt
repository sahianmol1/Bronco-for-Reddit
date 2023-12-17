package com.bestway.home_data.remote.api.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get

@SuppressWarnings("ReturnCount")
suspend inline fun <reified T> HttpClient.getSafeResponse(urlString: String): Result<T> {
    try {
        val response = this.get(urlString)
        if (response.status.value in ApiConstants.SUCCESS_RESPONSE_RANGE) {
            return Result.success(response.body())
        }
    } catch (e: RedirectResponseException) {
        return Result.failure(e)
    } catch (e: ClientRequestException) {
        return Result.failure(e)
    } catch (e: ServerResponseException) {
        return Result.failure(e)
    }

    return Result.failure(
        Throwable(ApiConstants.UNKNOWN_ERROR)
    )
}
