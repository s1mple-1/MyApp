package ru.s1mple.myapp.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.s1mple.myapp.R
import ru.s1mple.myapp.data.Actor

class ActorsAdapter() : RecyclerView.Adapter<ActorViewHolder>() {

    private var actors: List<Actor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        if (actors.isNotEmpty()) holder.onBind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActor: List<Actor>) {
        actors = newActor
        notifyDataSetChanged()
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val actorName: TextView = itemView.findViewById(R.id.actor_name_film_details)
    private val actorImage: ImageView = itemView.findViewById(R.id.actor_image)

    fun onBind(actor: Actor) {
        Picasso.get().load("$IMAGE_PATH${actor.profilePath}")
            .into(actorImage)
        actorName.text = actor.name
    }
}

private const val IMAGE_PATH = "https://image.tmdb.org/t/p/original"