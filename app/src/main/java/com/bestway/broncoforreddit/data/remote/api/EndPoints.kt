package com.bestway.broncoforreddit.data.remote.api

object EndPoints {
    private const val BASE_URL = "https://www.reddit.com"

    const val HOT = "$BASE_URL/hot.json"
    const val NEW = "$BASE_URL/new.json"
    const val TOP = "$BASE_URL/top.json"
    const val BEST = "$BASE_URL/best.json"
    const val RISING = "$BASE_URL/rising.json"
    const val CONTROVERSIAL = "$BASE_URL/controversial.json"
}