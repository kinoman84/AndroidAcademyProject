package ru.alexeybuchnev.androidacademyprojeckt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActorAdapter : RecyclerView.Adapter<ActorItemViewHolder>() {

    val actorsList = arrayListOf(1, 2, 3, 4, 5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        return ActorItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: ActorItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return actorsList.size
    }
}

class ActorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}