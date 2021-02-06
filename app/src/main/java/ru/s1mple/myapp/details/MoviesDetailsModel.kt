package ru.s1mple.myapp.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.s1mple.myapp.data.Actor
import ru.s1mple.myapp.data.MovieDetails
import ru.s1mple.myapp.data.MoviesDataRepository

class MoviesDetailsModel(
    private val dataRepository: MoviesDataRepository
) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "Throwable : $throwable")
    }

    private val mutableMovieLiveData = MutableLiveData<MovieDetails>()

    val movieLiveData: LiveData<MovieDetails> get() = mutableMovieLiveData

    private val mutableMovieActorsLiveData = MutableLiveData<List<Actor>>()

    val movieActorsLiveData: LiveData<List<Actor>> get() = mutableMovieActorsLiveData

    fun onViewCreated(mId: Long) {
        viewModelScope.launch(coroutineExceptionHandler) {
            mutableMovieLiveData.value = dataRepository.getMovieById(mId)
            mutableMovieActorsLiveData.value = dataRepository.getActorsByMovieId(mId)
        }
    }
}