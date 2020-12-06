package ru.s1mple.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter() : RecyclerView.Adapter<ActorViewHolder>() {

   private var actors : List<Actor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActors : List<Actor>) {
        actors = newActors
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val actorName : TextView = itemView.findViewById(R.id.actor_name_film_details)
    private val actorImage : ImageView = itemView.findViewById(R.id.actor_image)

    fun onBind(actor: Actor) {
        actorName.text = actor.fullName
        actorImage.setImageResource(actor.actorImage)
    }
}