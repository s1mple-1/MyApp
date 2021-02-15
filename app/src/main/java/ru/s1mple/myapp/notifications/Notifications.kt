package ru.s1mple.myapp.notifications

import ru.s1mple.myapp.data.Movie

interface Notifications {
    fun initialize()
    fun showNotification(movie: Movie)
}