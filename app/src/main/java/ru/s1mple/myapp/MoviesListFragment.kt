package ru.s1mple.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoviesListFragment : Fragment() {

    private var filmsClickListener : FilmClickListener? = null
    private var recycler: RecyclerView? = null

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

        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.films_recycler)
        recycler?.apply {
            layoutManager = GridLayoutManager(context, RECYCLER_COLUMN_COUNTER)
            adapter = FilmsListAdapter(filmsClickListener)
        }
    }

    override fun onStart() {
        super.onStart()

        updateData()
    }

    override fun onDetach() {
        super.onDetach()

        filmsClickListener = null
        recycler = null
    }

    private fun updateData() {
        (recycler?.adapter as? FilmsListAdapter)?.bindFilms(FilmsDataSource().getFilms())
    }

    interface FilmClickListener {
        fun onClick(filmId: Int)
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}

private const val RECYCLER_COLUMN_COUNTER = 2