package ru.s1mple.myapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.s1mple.myapp.data.MoviesDataRepository
import ru.s1mple.myapp.data.MoviesDataRepositoryImp
import ru.s1mple.myapp.database.MoviesLocalDataBase

class MyApp : Application(), MyAppComponent {

    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var moviesDataRepository: MoviesDataRepository

    override fun onCreate() {
        super.onCreate()
        moviesDataRepository = MoviesDataRepositoryImp(MoviesLocalDataBase.createDataBase(this))
        viewModelFactory = ViewModelFactory(moviesDataRepository)
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory
}

fun Context.appComponent() = (applicationContext as MyApp)

fun Fragment.appComponent() = requireContext().appComponent()