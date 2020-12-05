package ru.s1mple.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MoviesDetailsFragment() : Fragment() {

    private var listener: BackListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.back_button).apply {
            setOnClickListener {
                listener?.backToMain()
            }
        }

        val filmId = this.arguments!!.getInt("FILM_ID")
        val film = FilmsDataSource().getFilmById(filmId)

        val headerImage: ImageView = view.findViewById(R.id.film_details_header_image)
        headerImage.setImageResource(film.filmImageDetails)
        val ageRating: TextView = view.findViewById(R.id.age_rating_details)
        ageRating.text = film.ageRating
        val filmTitle: TextView = view.findViewById(R.id.film_title_details)
        filmTitle.text = film.filmTitleDetails
        val tagLine: TextView = view.findViewById(R.id.tag_line_details)
        tagLine.text = film.tagLine
        val reviewsCount: TextView = view.findViewById(R.id.reviews_count_details)
        reviewsCount.text = film.filmReviewsCount
        val filmDescription: TextView = view.findViewById(R.id.film_description)
        filmDescription.text = film.filmDescription

        val firstStar: ImageView = view.findViewById(R.id.star1)
        val secondStar: ImageView = view.findViewById(R.id.star2)
        val thirdStar: ImageView = view.findViewById(R.id.star3)
        val fourthStar: ImageView = view.findViewById(R.id.star4)
        val fifthStar: ImageView = view.findViewById(R.id.star5)
        val ratingList = listOf(firstStar, secondStar, thirdStar, fourthStar, fifthStar)

        for (i in 0 until film.rating) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_pink)
        }
    }

    fun setListener(l: BackListener) {
        listener = l
    }
}

interface BackListener {
    fun backToMain()
}