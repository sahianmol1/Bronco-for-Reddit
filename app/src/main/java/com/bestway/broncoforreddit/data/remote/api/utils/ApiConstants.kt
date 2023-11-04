package com.bestway.broncoforreddit.data.remote.api.utils

object ApiConstants {
    const val UNKNOWN_ERROR = "Unknown Error"
    private const val SUCCESS_RESPONSE_200 = 200
    private const val SUCCESS_RESPONSE_299 = 299
    val SUCCESS_RESPONSE_RANGE = SUCCESS_RESPONSE_200..SUCCESS_RESPONSE_299

    const val LIMIT_PER_PAGE = 25
}

object EndPoints {
    private const val BASE_URL = "https://www.reddit.com"

    const val HOT = "$BASE_URL/hot.json"
    const val NEW = "$BASE_URL/new.json"
    const val TOP = "$BASE_URL/top.json"
    const val BEST = "$BASE_URL/best.json"
    const val RISING = "$BASE_URL/rising.json"
    const val CONTROVERSIAL = "$BASE_URL/controversial.json"
}
