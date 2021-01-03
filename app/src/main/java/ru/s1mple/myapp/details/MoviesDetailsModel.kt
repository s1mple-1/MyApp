package ru.s1mple.myapp.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.s1mple.myapp.data.Movie
import ru.s1mple.myapp.data.MoviesDataSource

class MoviesDetailsModel(
    private val dataSource: MoviesDataSource
) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "Throwable : $throwable")
    }

    private val mMovieLiveData = MutableLiveData<Movie>()

    val movieLiveData: LiveData<Movie> get() = mMovieLiveData

    fun onViewCreated(mId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            mMovieLiveData.value = dataSource.getMovieById(mId)
        }
    }

}