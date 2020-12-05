package ru.s1mple.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MoviesDetailsFragment : Fragment() {

    private var backListener: BackListener? = null
    private var filmId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BackListener) {
            backListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.back_button).setOnClickListener {
            backListener?.backToMain()
        }

        filmId = arguments?.getInt("KEY_FILM_ID") ?: filmId
        val film = FilmsDataSource().getFilmById(filmId)

        view.findViewById<ImageView>(R.id.film_details_header_image).setImageResource(film.filmImageDetails)
        view.findViewById<TextView>(R.id.age_rating_details).text = film.ageRating
        view.findViewById<TextView>(R.id.film_title_details).text = film.filmTitleDetails
        view.findViewById<TextView>(R.id.tag_line_details).text = film.tagLine
        view.findViewById<TextView>(R.id.reviews_count_details).text = film.filmReviewsCount
        view.findViewById<TextView>(R.id.film_description).text = film.filmDescription

        val ratingList = listOf<ImageView>(
            view.findViewById(R.id.star1),
            view.findViewById(R.id.star2),
            view.findViewById(R.id.star3),
            view.findViewById(R.id.star4),
            view.findViewById(R.id.star5)
        )
        for (i in 0 until film.rating) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_pink)
        }
    }

    override fun onDetach() {
        super.onDetach()

        backListener = null
    }

    companion object {
        fun newInstance(filmId: Int): MoviesDetailsFragment {
            val fragment = MoviesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_FILM_ID, filmId)
                }
            }
            return fragment
        }
    }

    interface BackListener {
        fun backToMain()
    }
}

private const val KEY_FILM_ID = "KEY_FILM_ID"