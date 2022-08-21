package com.peter.asteroids.framework.datasource.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.peter.asteroids.business.repository.INasaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import javax.inject.Inject

class MyWorker(
    appContext: Context, params: WorkerParameters,
    private val repository: INasaRepository
) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "DatabaseWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            repository.getAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}