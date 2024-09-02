package com.anmolsahi.benchmark

import android.content.Context
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction

internal fun MacrobenchmarkScope.homeScreenFlow(context: Context) {
    pressHome()
    startActivityAndWait()

    device.findObject(By.text("Hot"))?.click()
    device.waitForIdle()
    flingRedditPosts(context)

    device.findObject(By.text("New"))?.click()
    device.waitForIdle()
    flingRedditPosts(context)

    device.findObject(By.text("Top"))?.click()
    device.waitForIdle()
    flingRedditPosts(context)

    device.findObject(By.text("Best"))?.click()
    device.waitForIdle()
    flingRedditPosts(context)

    device.findObject(By.text("Rising"))?.click()
    device.waitForIdle()
    flingRedditPosts(context)

    device.findObject(By.text("Controversial"))?.click()
    device.waitForIdle()
    flingRedditPosts(context)
}

private fun MacrobenchmarkScope.flingRedditPosts(context: Context) {
    device.findObject(By.desc(context.getString(R.string.content_description_home_screen_list)))
        ?.fling(Direction.DOWN)
    device.waitForIdle()
}
