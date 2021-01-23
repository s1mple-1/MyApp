package ru.s1mple.myapp.details

import MovieDetails
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.s1mple.myapp.data.Actor
import ru.s1mple.myapp.data.MoviesDataSource

class MoviesDetailsModel(
    private val dataSource: MoviesDataSource
) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "Throwable : $throwable")
    }

    private val mMovieLiveData = MutableLiveData<MovieDetails>()

    val movieLiveData: LiveData<MovieDetails> get() = mMovieLiveData

    private val mMovieActorsLiveData = MutableLiveData<List<Actor>>()

    val movieActorsLiveData: LiveData<List<Actor>> get() = mMovieActorsLiveData

    fun onViewCreated(mId: Long) {
        viewModelScope.launch(coroutineExceptionHandler) {
            mMovieLiveData.value = dataSource.getMovieById(mId)
            mMovieActorsLiveData.value = dataSource.getActorsByMovieId(mId)
        }
    }
}