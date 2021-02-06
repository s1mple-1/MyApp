package ru.s1mple.myapp.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.s1mple.myapp.data.Movie
import ru.s1mple.myapp.data.MoviesDataRepository

class MoviesListModel(
    private val dataRepository: MoviesDataRepository
) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "Throwable : $throwable")
    }

    private val mutableMoviesLiveData = MutableLiveData<List<Movie>>(emptyList())
    val moviesLiveData: LiveData<List<Movie>> get() = mutableMoviesLiveData

    fun onViewCreated() {
        viewModelScope.launch(coroutineExceptionHandler) {
            mutableMoviesLiveData.value = dataRepository.getMovies(false)
        }
    }
}