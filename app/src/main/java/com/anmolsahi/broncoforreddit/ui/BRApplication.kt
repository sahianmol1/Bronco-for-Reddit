package com.anmolsahi.broncoforreddit.ui

import android.app.Application
import com.anmolsahi.broncoforreddit.utils.queueDBCleanupWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BRApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        queueDBCleanupWorker()
    }
}
