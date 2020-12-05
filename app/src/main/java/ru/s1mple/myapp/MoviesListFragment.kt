package ru.s1mple.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MoviesListFragment : Fragment() {

    private var filmsListClickListener : FilmsListClickListener? = null
    private var recycler: RecyclerView? = null

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
            recycler?.adapter = FilmsListAdapter(filmsListClickListener)
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    override fun onDetach() {
        recycler = null
        super.onDetach()
    }

    private fun updateData() {
        (recycler?.adapter as? FilmsListAdapter)?.apply {
            bindFilms(FilmsDataSource().getFilms())
        }
    }

    fun setListener(l : FilmsListClickListener) {
        filmsListClickListener = l
    }
}