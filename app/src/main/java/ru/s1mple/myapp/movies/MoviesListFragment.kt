package ru.s1mple.myapp.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.s1mple.myapp.BaseFragment
import ru.s1mple.myapp.R
import ru.s1mple.myapp.data.Movie

class MoviesListFragment : BaseFragment() {

    private var filmsClickListener : FilmClickListener? = null
    private var recycler: RecyclerView? = null
    private var coroutineScope = createCoroutineScope()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        coroutineScope = createCoroutineScope()
    }

    private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.Main)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FilmClickListener) {
            filmsClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.films_recycler)
        recycler?.apply {
            layoutManager = GridLayoutManager(context, RECYCLER_COLUMN_COUNTER)
            adapter = MoviesListAdapter(filmsClickListener)
        }
        updateData()
    }

    override fun onDetach() {
        super.onDetach()

        filmsClickListener = null
        recycler = null
        coroutineScope.cancel()
    }

    private fun updateData() {
        coroutineScope.launch(coroutineExceptionHandler) {
            val movies = dataProvider?.dataSource()?.getMovies() ?: emptyList()
            updateMovies(movies)
        }
    }

    private fun updateMovies(movies : List<Movie>) {
        (recycler?.adapter as? MoviesListAdapter)?.bindMovies(movies)
    }

    interface FilmClickListener {
        fun onClick(filmId: Int)
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}

private const val RECYCLER_COLUMN_COUNTER = 2