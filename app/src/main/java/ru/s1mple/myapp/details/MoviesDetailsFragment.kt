package ru.s1mple.myapp.details

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.s1mple.myapp.BaseFragment
import ru.s1mple.myapp.R
import ru.s1mple.myapp.appComponent
import ru.s1mple.myapp.data.Actor
import ru.s1mple.myapp.data.MovieDetails

class MoviesDetailsFragment : BaseFragment() {

    private lateinit var movieDetailsModel: MoviesDetailsModel

    private var backListener: BackListener? = null
    private var filmId: Long = 0
    private var recyclerView: RecyclerView? = null

    private var detailsHeaderImage: ImageView? = null
    private var ageRating: TextView? = null
    private var filmTitle: TextView? = null
    private var genresLine: TextView? = null
    private var ratingList: List<ImageView>? = null
    private var reviewsCount: TextView? = null
    private var filmDescription: TextView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BackListener) {
            backListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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

        setBackListener(view)
        filmId = arguments?.getLong("KEY_FILM_ID") ?: filmId
        detailsHeaderImage?.transitionName = "film_image_details_${filmId}"
        setUpViews(view)
        setUpModel(filmId)
    }

    private fun setBackListener(view: View) {
        view.findViewById<View>(R.id.back_button).setOnClickListener {
            backListener?.backToMain()
        }
    }

    private fun setUpViews(view: View) {
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

    private fun setUpModel(mId: Long) {
        movieDetailsModel = ViewModelProvider(
            this,
            appComponent().viewModelFactory()
        ).get(MoviesDetailsModel::class.java)
        movieDetailsModel.onViewCreated(mId)
        movieDetailsModel.movieLiveData.observe(this.viewLifecycleOwner) {
            updateViews(it)
        }
        movieDetailsModel.movieActorsLiveData.observe(this.viewLifecycleOwner) {
            updateActors(it)
        }
    }

    private fun updateViews(movie: MovieDetails) {
        val backdrop = "$IMAGE_PATH${movie.backdropPath}"

        Glide.with(detailsHeaderImage!!).load(backdrop)
            .addListener(object: RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    detailsHeaderImage!!.setImageDrawable(resource)
                    return true
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }
            })
            .into(detailsHeaderImage!!)

        ageRating?.text = "${movie.getMinimumAge()}+"
        filmTitle?.text = movie.title

        val genres = movie.genres.joinToString(transform = {it.name})
        genresLine?.text = genres

        val reviews = movie.voteCount
        reviewsCount?.text = "$reviews REVIEWS"
        filmDescription?.text = movie.overview

        val rating = (movie.voteAverage.toInt()) / 2
        for (i in 0 until rating) {
            ratingList?.get(i)?.setImageResource(R.drawable.ic_star_icon_pink)
        }
        for (i in rating until MAX_FILM_RATING_VALUE) {
            ratingList?.get(i)?.setImageResource(R.drawable.ic_star_icon_gray)
        }
    }

    private fun updateActors(actors: List<Actor>) {
        (recyclerView?.adapter as? ActorsAdapter)?.bindActors(actors)
    }

    override fun onDetach() {
        super.onDetach()

        recyclerView = null
        backListener = null
    }

    companion object {
        fun newInstance(mId: Long): MoviesDetailsFragment {
            return MoviesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(KEY_FILM_ID, mId)
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
private const val IMAGE_PATH = "https://image.tmdb.org/t/p/original"
