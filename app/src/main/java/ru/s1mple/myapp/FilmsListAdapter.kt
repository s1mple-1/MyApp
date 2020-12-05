package ru.s1mple.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmsListAdapter(
    private val filmClickListener: MoviesListFragment.FilmClickListener?
) : RecyclerView.Adapter<FilmsViewHolder>() {

    private var films = listOf<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_list, parent, false)
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.onBind(films[position])
        holder.itemView.setOnClickListener {
            filmClickListener?.onClick(films[position].filmId)
        }
    }

    override fun getItemCount(): Int = films.size

    fun bindFilms(newFilms: List<Film>) {
        films = newFilms
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

    fun onBind(film: Film) {
        filmImageMain.setImageResource(film.filmImageMain)
        if (film.hasLike) {
            like.setImageResource(R.drawable.ic_like)

        } else {
            like.setImageResource(R.drawable.ic_unlike)
        }

        val rating = film.rating
        for (i in 0 until rating) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_pink)
        }
        for (i in rating until MAX_FILM_RATING_VALUE) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_gray)
        }

        ageRating.text = film.ageRating
        filmTitleMain.text = film.filmTitleMain
        filmDurationMain.text = film.filmDurationMain
        filmReviewsCount.text = film.filmReviewsCount
        tagLine.text = film.tagLine
    }
}

private const val MAX_FILM_RATING_VALUE = 5

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context