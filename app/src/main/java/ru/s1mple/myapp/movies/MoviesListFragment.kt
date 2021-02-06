package ru.s1mple.myapp.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.s1mple.myapp.BaseFragment
import ru.s1mple.myapp.R
import ru.s1mple.myapp.appComponent
import ru.s1mple.myapp.background.MovieUpdateWorker
import ru.s1mple.myapp.data.Movie

class MoviesListFragment : BaseFragment() {

    private lateinit var moviesListModel: MoviesListModel

    private var filmsClickListener: FilmClickListener? = null
    private var recycler: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FilmClickListener) {
            filmsClickListener = context
            MovieUpdateWorker.startWork(context)
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

        setUpViews(view)
        setUpViewModel()
    }

    private fun setUpViews(view: View) {
        recycler = view.findViewById(R.id.films_recycler)
        recycler?.apply {
            layoutManager = GridLayoutManager(context, RECYCLER_COLUMN_COUNTER)
            adapter = MoviesListAdapter(filmsClickListener)
        }
    }

    private fun setUpViewModel() {
        moviesListModel = ViewModelProvider(
            this,
            appComponent().viewModelFactory()
        ).get(MoviesListModel::class.java)
        moviesListModel.onViewCreated()
        moviesListModel.moviesLiveData.observe(this.viewLifecycleOwner) {
            updateMovies(it)
        }
    }

    override fun onDetach() {
        super.onDetach()

        filmsClickListener = null
        recycler = null
    }

    private fun updateMovies(movies: List<Movie>) {
        (recycler?.adapter as? MoviesListAdapter)?.bindMovies(movies)
    }

    interface FilmClickListener {
        fun onClick(mId: Long)
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}

private const val RECYCLER_COLUMN_COUNTER = 2