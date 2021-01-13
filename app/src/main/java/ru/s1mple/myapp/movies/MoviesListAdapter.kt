package ru.s1mple.myapp.movies

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.s1mple.myapp.R
import ru.s1mple.myapp.data.Movie
import ru.s1mple.myapp.data.MoviesDataSourceImpl

class MoviesListAdapter(
    private val filmClickListener: MoviesListFragment.FilmClickListener?
) : RecyclerView.Adapter<FilmsViewHolder>() {

    private var movies: List<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_list, parent, false)
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener {
            filmClickListener?.onClick(movies[position].id)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovie: List<Movie>) {
        movies = newMovie
        notifyDataSetChanged()
    }
}

class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmImageMain: ImageView = itemView.findViewById(R.id.film_image_main)
    private val ageRating: TextView = itemView.findViewById(R.id.age_rating_main)
    private val filmTitleMain: TextView = itemView.findViewById(R.id.film_title_main)
    private val filmDurationMain: TextView = itemView.findViewById(R.id.film_duration_main)
    private val filmReviewsCount: TextView = itemView.findViewById(R.id.reviews_count_main)
    private val tagLine: TextView = itemView.findViewById(R.id.tag_line_details)
    private val like: ImageView = itemView.findViewById(R.id.like_main)
    private val ratingList = listOf<ImageView>(
        itemView.findViewById(R.id.star1),
        itemView.findViewById(R.id.star2),
        itemView.findViewById(R.id.star3),
        itemView.findViewById(R.id.star4),
        itemView.findViewById(R.id.star5),
    )

    fun onBind(movie: Movie) {

        val moviePoster = "$IMAGE_PATH${movie.posterPath}"

        filmImageMain.load(moviePoster) {
            error(R.drawable.avengers_main)
        }

//        if (movie.hasLike) { //TODO реализация лайка
//            like.setImageResource(R.drawable.ic_like)
//
//        } else {
//            like.setImageResource(R.drawable.ic_unlike)
//        }
        val genresList = MoviesDataSourceImpl.getGenresList()
        val rating = (movie.voteAverage.toInt()) / 2
        for (i in 0 until rating) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_pink)
        }
        for (i in rating until MAX_FILM_RATING_VALUE) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_gray)
        }
        ageRating.text = "${movie.getMinimumAge()}+"
        filmTitleMain.text = movie.title

        filmDurationMain.visibility = INVISIBLE
        val reviews = movie.voteCount
        filmReviewsCount.text = "$reviews REVIEWS"
        tagLine.text =
            genresList.filter { it.id in movie.genreIDS }.joinToString(transform = { it.name })
    }
}

private const val MAX_FILM_RATING_VALUE = 5
private const val IMAGE_PATH = "https://image.tmdb.org/t/p/original"
