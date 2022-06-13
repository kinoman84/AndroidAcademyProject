package ru.alexeybuchnev.androidacademyprojeckt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class FilmsAdapter(val clickListener: FragmentMoviesList.Callbacks?) :
    RecyclerView.Adapter<FilmsAdapter.FilmListItemViewHolder>() {
    private var filmsList = listOf<Movie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return FilmListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) {
        holder.bindFilm(filmsList[position])

        holder.itemView.setOnClickListener { _ ->
            clickListener?.onFilmSelectedClick(position)
        }
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    fun bindMovies(moviesList: List<Movie>) {
        this.filmsList = moviesList
    }

    class FilmListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var movie: Movie
        private val context: Context = itemView.context

        private val filmNameTextView: TextView = itemView.findViewById(R.id.movie_name_text)
        private val filmPosterImageView: ImageView =
            itemView.findViewById(R.id.film_poster_image_view)
        private val reviewersCountTextView: TextView =
            itemView.findViewById(R.id.reviewers_count_text_view)


        fun bindFilm(film: Movie) {
            this.movie = film

            filmNameTextView.text = film.title

            reviewersCountTextView.text = String.format(
                context.resources.getString(R.string.reviews_count),
                movie.reviewCount
            )

            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .apply(imageOption)
                .into(filmPosterImageView)
        }

        companion object {
            private val imageOption = RequestOptions()
                .placeholder(R.drawable.film_poster_avenges_end_game)
                .fallback(R.drawable.film_poster_avenges_end_game)
                .centerCrop()
        }

    }
}

