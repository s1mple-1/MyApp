package ru.s1mple.myapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.s1mple.myapp.data.MoviesDataSource
import ru.s1mple.myapp.details.MoviesDetailsModel
import ru.s1mple.myapp.movies.MoviesListModel

class ViewModelFactory(private val dataSource: MoviesDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MoviesListModel::class.java -> MoviesListModel(dataSource)
            MoviesDetailsModel::class.java -> MoviesDetailsModel(dataSource)
            else -> throw IllegalArgumentException("ViewModel of class:$modelClass is not supported")
        } as T
    }
}