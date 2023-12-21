package ru.s1mple.myapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.s1mple.myapp.data.MoviesDataRepository
import ru.s1mple.myapp.details.MoviesDetailsModel
import ru.s1mple.myapp.movies.MoviesListModel

class ViewModelFactory(private val dataRepository: MoviesDataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when (modelClass) {
            MoviesListModel::class.java -> MoviesListModel(dataRepository)
            MoviesDetailsModel::class.java -> MoviesDetailsModel(dataRepository)
            else -> throw IllegalArgumentException("ViewModel of class:$modelClass is not supported")
        } as T
    }
}