package ru.s1mple.myapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.s1mple.myapp.data.MoviesDataSourceImpl

class MyApp : Application(), MyAppComponent {

    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        viewModelFactory = ViewModelFactory(MoviesDataSourceImpl(this))
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory
}

fun Context.appComponent() = (applicationContext as MyApp)

fun Fragment.appComponent() = requireContext().appComponent()