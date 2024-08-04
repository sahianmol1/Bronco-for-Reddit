package com.anmolsahi.broncoforreddit.database.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.anmolsahi.domain.repositories.HomeRepository

@SuppressWarnings("LongParameterList")
class DatabaseCleanupWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val homeRepository: HomeRepository,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        homeRepository.deleteStalePosts()
        return Result.success()
    }
}
