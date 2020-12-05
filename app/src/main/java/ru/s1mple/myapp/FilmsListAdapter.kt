package ru.s1mple.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FilmsListAdapter(
    private val filmsListClickListener: FilmsListClickListener?
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
            filmsListClickListener?.onClick(films[position])
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
    private val firstStar: ImageView = itemView.findViewById(R.id.star1)
    private val secondStar: ImageView = itemView.findViewById(R.id.star2)
    private val thirdStar: ImageView = itemView.findViewById(R.id.star3)
    private val fourthStar: ImageView = itemView.findViewById(R.id.star4)
    private val fifthStar: ImageView = itemView.findViewById(R.id.star5)

    private val ratingList = listOf(firstStar, secondStar, thirdStar, fourthStar, fifthStar)

    fun onBind(film: Film) {
        filmImageMain.setImageResource(film.filmImageMain)
        if (film.hasLike) {
            like.setImageResource(R.mipmap.like)
        }
        for (i in 0 until film.rating) {
            ratingList[i].setImageResource(R.drawable.ic_star_icon_pink)
        }
        ageRating.text = film.ageRating
        filmTitleMain.text = film.filmTitleMain
        filmDurationMain.text = film.filmDurationMain
        filmReviewsCount.text = film.filmReviewsCount
        tagLine.text = film.tagLine
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

interface FilmsListClickListener {
    fun onClick(film: Film)
}