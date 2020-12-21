package ru.s1mple.myapp

import android.app.Application
import ru.s1mple.myapp.data.MoviesDataSource
import ru.s1mple.myapp.data.MoviesDataSourceImpl

class MyApp : Application(), IDataProvider {

    private lateinit var moviesDataSource : MoviesDataSource

    override fun onCreate() {
        super.onCreate()

        moviesDataSource = MoviesDataSourceImpl(this)
    }

    override fun dataSource(): MoviesDataSource = moviesDataSource
}

interface IDataProvider {
    fun dataSource() : MoviesDataSource
}