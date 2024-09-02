package com.anmolsahi.broncoforreddit.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.anmolsahi.broncoforreddit.database.worker.DatabaseCleanupWorker
import java.util.concurrent.TimeUnit

fun Context.queueDBCleanupWorker() {
    val work = PeriodicWorkRequestBuilder<DatabaseCleanupWorker>(1, TimeUnit.DAYS)
        .setConstraints(Constraints(requiresDeviceIdle = true))
        .addTag("cleanup")
        .build()

    WorkManager.getInstance(this)
        .enqueueUniquePeriodicWork("cleanup", ExistingPeriodicWorkPolicy.KEEP, work)
}
