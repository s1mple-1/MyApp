package ru.s1mple.myapp.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import ru.s1mple.myapp.BaseFragment
import ru.s1mple.myapp.R
import ru.s1mple.myapp.data.Movie

class MoviesDetailsFragment : BaseFragment() {

    private var backListener: BackListener? = null
    private var filmId = 0
    private var recyclerView : RecyclerView? = null

    private var detailsHeaderImage : ImageView? = null
    private var ageRating : TextView? = null
    private var filmTitle : TextView? = null
    private var genresLine : TextView? = null
    private var ratingList : List<ImageView>? = null
    private var reviewsCount : TextView? = null
    private var filmDescription : TextView? = null

    private var coroutineScope = createCoroutineScope()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        coroutineScope = createCoroutineScope()
    }

    private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.Main)

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
        findViews(view)
        updateData(filmId)
    }

    private fun findViews(view: View) {
        detailsHeaderImage = view.findViewById(R.id.film_details_header_image)
        ageRating = view.findViewById(R.id.age_rating_details)
        filmTitle = view.findViewById(R.id.film_title_details)
        genresLine = view.findViewById(R.id.tag_line_details)
        reviewsCount = view.findViewById(R.id.reviews_count_details)
        filmDescription = view.findViewById(R.id.film_description)
        ratingList = listOf(
            view.findViewById(R.id.star1),
            view.findViewById(R.id.star2),
            view.findViewById(R.id.star3),
            view.findViewById(R.id.star4),
            view.findViewById(R.id.star5),
        )
        recyclerView = view.findViewById(R.id.actors_recycler)
        recyclerView?.adapter = ActorsAdapter()
    }

    private fun updateData(filmId : Int) {
        coroutineScope.launch(coroutineExceptionHandler) {
            dataProvider?.dataSource()?.getMovieById(filmId)?.apply {
                updateViews(this)
            }
        }
    }

    private suspend  fun updateViews(movie: Movie) = withContext(Dispatchers.Main) {
        Picasso.get().load(movie.backdrop).into(detailsHeaderImage)

        val minimumAge = movie.minimumAge
        ageRating?.text = "$minimumAge+"
        filmTitle?.text = movie.title

        var genres = "" //TODO сделать более нормально решение
        for (i in movie.genres) {
            genres = genres + " " + i.name
        }
        genresLine?.text = genres
        val reviews = movie.numberOfRatings
        reviewsCount?.text = "$reviews REVIEWS"
        filmDescription?.text = movie.overview

        val rating = (movie.ratings?.toInt() ?: 0)/2
        for (i in 0 until rating) {
            ratingList?.get(i)?.setImageResource(R.drawable.ic_star_icon_pink)
        }
        for (i in rating until MAX_FILM_RATING_VALUE) {
            ratingList?.get(i)?.setImageResource(R.drawable.ic_star_icon_gray)
        }

        (recyclerView?.adapter as? ActorsAdapter)?.bindActors(movie.actors)
    }

    override fun onDetach() {
        super.onDetach()

        recyclerView = null
        backListener = null
        coroutineScope.cancel()
    }

    companion object {
        fun newInstance(filmId: Int): MoviesDetailsFragment {
            return MoviesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_FILM_ID, filmId)
                }
            }
        }
    }

    interface BackListener {
        fun backToMain()
    }
}

private const val KEY_FILM_ID = "KEY_FILM_ID"
private const val MAX_FILM_RATING_VALUE = 5