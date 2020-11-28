package ru.s1mple.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.first_film).apply {
            setOnClickListener {
                fragmentManager?.beginTransaction()?.apply {
                    add(R.id.mainFrame, FragmentMoviesDetails())
                    commit()
                }
            }
        }
    }


}