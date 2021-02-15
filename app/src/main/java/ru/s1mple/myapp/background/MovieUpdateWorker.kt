package ru.s1mple.myapp.background

import android.content.Context
import androidx.work.*
import ru.s1mple.myapp.data.MoviesDataRepository
import ru.s1mple.myapp.notifications.Notifications
import ru.s1mple.myapp.notifications.NotificationsImpl
import java.util.concurrent.TimeUnit

class MovieUpdateWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private var notifications: Notifications = NotificationsImpl(context)

    override suspend fun doWork(): Result {
        return try {
            notifications.initialize()
            updateMoviesData(notifications)
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

        fun setUpRepository(moviesDataRepository: MoviesDataRepository) {
            this.moviesDataRepository = moviesDataRepository
        }

        fun startWork(context: Context) {
            WorkManager.getInstance(context).enqueue(request)
        }

        private suspend fun updateMoviesData(notifications: Notifications) {
            if(moviesDataRepository.updateMovies() > 0) {
                notifications.showNotification(moviesDataRepository.getTopRatedMovie())
            }
        }
    }
}