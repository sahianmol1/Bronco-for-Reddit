package com.anmolsahi.data.remote.utils

import com.anmolsahi.data.apihelper.ApiConstants.BASE_URL

internal object EndPoints {
    const val HOT = "$BASE_URL/hot.json"
    const val NEW = "$BASE_URL/new.json"
    const val TOP = "$BASE_URL/top.json"
    const val BEST = "$BASE_URL/best.json"
    const val RISING = "$BASE_URL/rising.json"
    const val CONTROVERSIAL = "$BASE_URL/controversial.json"
}
