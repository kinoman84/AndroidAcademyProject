package ru.alexeybuchnev.androidacademyprojeckt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter(val clickListener: FragmentMoviesList.Callbacks?) : RecyclerView.Adapter<FilmListItemViewHolder>() {
    private var filmsList = listOf<Film>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return FilmListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {_ ->
            //TODO предавать id фильма
            clickListener?.onFilmSelectedClick(position)
        }

        holder.bindFilm(filmsList[position])
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    fun bindFilms(filmsList: List<Film>) {
        this.filmsList = filmsList
    }
}

class FilmListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var film: Film

    private val filmNameTextView = itemView.findViewById<TextView>(R.id.filmNameTextView)

    fun bindFilm(film: Film) {
        this.film = film
        filmNameTextView.text = film.name
    }

}