package ru.alexeybuchnev.androidacademyprojeckt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class FilmsAdapter(val clickListener: FragmentMoviesList.Callbacks?) : RecyclerView.Adapter<FilmListItemViewHolder>() {
    private var filmsList = listOf<Movie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return FilmListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {_ ->
            clickListener?.onFilmSelectedClick(position)
        }

        holder.bindFilm(filmsList[position])
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    fun bindMovies(moviesList: List<Movie>) {
        this.filmsList = moviesList
    }
}

class FilmListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var movie: Movie

    private val filmNameTextView: TextView = itemView.findViewById(R.id.movie_name_text)
    private var filmPosterImageView: ImageView = itemView.findViewById(R.id.movie_logo_image)

    fun bindFilm(film: Movie) {
        this.movie = film
        filmNameTextView.text = film.title
        //filmPosterImageView.setImageResource(film.imageResourceId)
    }

}