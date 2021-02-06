package ru.s1mple.myapp.background

import android.content.Context
import androidx.work.*
import ru.s1mple.myapp.data.MoviesDataRepository
import java.util.concurrent.TimeUnit

class MovieUpdateWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            updateMoviesData()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private lateinit var moviesDataRepository: MoviesDataRepository
        private const val TIME_PERIOD = 8L

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        private val request = PeriodicWorkRequest.Builder(MovieUpdateWorker::class.java, TIME_PERIOD, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        fun startWork(context: Context, moviesDataRepository: MoviesDataRepository) {
            this.moviesDataRepository = moviesDataRepository
            WorkManager.getInstance(context).enqueue(request)
        }

        private suspend fun updateMoviesData() {
            moviesDataRepository.getMovies(true)
        }
    }
}