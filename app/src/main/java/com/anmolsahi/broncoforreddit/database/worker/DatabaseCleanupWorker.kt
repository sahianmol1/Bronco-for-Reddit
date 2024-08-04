package com.anmolsahi.broncoforreddit.database.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.anmolsahi.domain.repositories.HomeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DatabaseCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val homeRepository: HomeRepository,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        homeRepository.deleteStalePosts()
        return Result.success()
    }
}
