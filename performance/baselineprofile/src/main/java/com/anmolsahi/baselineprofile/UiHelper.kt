package com.anmolsahi.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction

internal fun MacrobenchmarkScope.homeScreenFlow() {
    pressHome()
    startActivityAndWait()

    device.findObject(By.text("Hot"))?.click()
    device.waitForIdle()
    flingRedditPosts()

    device.findObject(By.text("New"))?.click()
    device.waitForIdle()
    flingRedditPosts()

    device.findObject(By.text("Top"))?.click()
    device.waitForIdle()
    flingRedditPosts()

    device.findObject(By.text("Best"))?.click()
    device.waitForIdle()
    flingRedditPosts()

    device.findObject(By.text("Rising"))?.click()
    device.waitForIdle()
    flingRedditPosts()

    device.findObject(By.text("Controversial"))?.click()
    device.waitForIdle()
    flingRedditPosts()
}

private fun MacrobenchmarkScope.flingRedditPosts() {
    device.findObject(By.res("home_screen_list"))?.fling(Direction.UP)
    device.waitForIdle()
}
