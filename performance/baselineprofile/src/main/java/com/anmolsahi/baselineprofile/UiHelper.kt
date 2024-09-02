package com.anmolsahi.baselineprofile

import android.content.Context
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2

internal fun MacrobenchmarkScope.homeScreenFlow(context: Context) {
    pressHome()
    startActivityAndWait()

    device.findObject(By.text("Hot"))?.click()
    device.waitForIdle()
    device.flingRedditPosts(context)

    device.findObject(By.text("New"))?.click()
    device.waitForIdle()
    device.flingRedditPosts(context)

    device.findObject(By.text("Top"))?.click()
    device.waitForIdle()
    device.flingRedditPosts(context)

    device.findObject(By.text("Best"))?.click()
    device.waitForIdle()
    device.flingRedditPosts(context)

    device.findObject(By.text("Rising"))?.click()
    device.waitForIdle()
    device.flingRedditPosts(context)

    device.findObject(By.text("Controversial"))?.click()
    device.waitForIdle()
    device.flingRedditPosts(context)
}

private fun UiDevice.flingRedditPosts(context: Context) {
    val element =
        findObject(By.desc(context.getString(R.string.content_description_home_screen_list)))
    flingElementDownUp(element)
    waitForIdle()
}

fun UiDevice.flingElementDownUp(element: UiObject2) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 5)

    element.fling(Direction.DOWN)
    waitForIdle()
    element.fling(Direction.UP)
}
