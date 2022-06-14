package ru.alexeybuchnev.androidacademyprojeckt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.alexeybuchnev.androidacademyprojeckt.model.Actor

class ActorAdapter(val actorsList: List<Actor>) : RecyclerView.Adapter<ActorItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        return ActorItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: ActorItemViewHolder, position: Int) {
        holder.bindActor(actorsList[position])
    }

    override fun getItemCount(): Int {
        return actorsList.size
    }
}

class ActorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private lateinit var actor: Actor

    private val actorImageView: ImageView = view.findViewById(R.id.actorImageView)
    private val actorNameTextView: TextView = view.findViewById(R.id.actorNameTextView)

    fun bindActor(actor: Actor) {
        this.actor = actor
        //actorImageView.setImageResource(actor.imageResourceId)
        actorNameTextView.text = actor.name

        Glide.with(itemView.context)
            .load(actor.imageUrl)
            .apply(imageOption)
            .into(actorImageView)
    }

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.actor_img_chris_evans)
            .fallback(R.drawable.actor_img_chris_evans)
            .centerCrop()
    }

}