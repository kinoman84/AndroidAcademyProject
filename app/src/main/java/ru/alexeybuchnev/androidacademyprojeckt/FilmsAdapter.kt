package ru.alexeybuchnev.androidacademyprojeckt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter(val clickListener: FragmentMoviesList.Callbacks?) : RecyclerView.Adapter<FilmListItemViewHolder>() {
    private var filmsList = listOf(1,2,3,4)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return FilmListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {_ ->
            //TODO предавать id фильма
            clickListener?.onFilmSelectedClick("film")
        }
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }
}

class FilmListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}