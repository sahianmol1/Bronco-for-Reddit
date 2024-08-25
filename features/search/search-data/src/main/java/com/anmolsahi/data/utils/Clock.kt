package com.anmolsahi.data.utils

interface Clock {
    fun currentTimeMillis(): Long
}

object SystemClock : Clock {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}
