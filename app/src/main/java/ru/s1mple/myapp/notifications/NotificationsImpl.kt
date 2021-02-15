package ru.s1mple.myapp.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import ru.s1mple.myapp.MainActivity
import ru.s1mple.myapp.R
import ru.s1mple.myapp.data.Movie


class NotificationsImpl(private val context: Context) : Notifications {

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(NEW_MOVIES_CHANNEL) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(
                    NEW_MOVIES_CHANNEL,
                    NotificationManagerCompat.IMPORTANCE_HIGH
                )
                    .setName("New movies")
                    .build()
            )
        }
    }

    override fun showNotification(movie: Movie) {
        val contentUri = "https://my.app/movie/${movie.id}".toUri()

        val builder = NotificationCompat.Builder(context, NEW_MOVIES_CHANNEL)
            .setContentTitle(movie.title)
            .setContentText(movie.overview)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

        notificationManagerCompat.notify(TAG_MOVIE, movie.id.toInt(), builder.build())
    }

    companion object {
        private const val NEW_MOVIES_CHANNEL = "new_movies"

        private const val REQUEST_CONTENT = 1

        private const val TAG_MOVIE = "movie"
    }
}