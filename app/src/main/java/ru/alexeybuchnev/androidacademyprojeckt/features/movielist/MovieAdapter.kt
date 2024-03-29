package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.alexeybuchnev.androidacademyprojeckt.R
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MovieAdapter(val clickListener: MoviesListFragment.Callbacks?, val listEndedHandler: Callbacks) :
    RecyclerView.Adapter<MovieAdapter.FilmListItemViewHolder>() {

    private var filmsList = listOf<Movie>()

    interface Callbacks {
        fun onListEnded()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return FilmListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) {

        val bindingMovie: Movie = filmsList[position]

        holder.bindFilm(bindingMovie)

        holder.itemView.setOnClickListener {
            clickListener?.onFilmSelectedClick(bindingMovie.id)
        }

        if (position == filmsList.size - 1) listEndedHandler.onListEnded()
        //Log.d("MovieAdapter", "end of list. position: $position")
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
        private val genresTextView: TextView = itemView.findViewById(R.id.genres_text_view)
        private val runningTimeTextView: TextView =
            itemView.findViewById(R.id.running_time_text_view)
        private val ageRestrictionsTextView: TextView =
            itemView.findViewById(R.id.age_restrictions_text_view)


        private val star1: View = itemView.findViewById(R.id.star1)
        private val star2: View = itemView.findViewById(R.id.star2)
        private val star3: View = itemView.findViewById(R.id.star3)
        private val star4: View = itemView.findViewById(R.id.star4)
        private val star5: View = itemView.findViewById(R.id.star5)

        fun bindFilm(film: Movie) {
            this.movie = film

            filmNameTextView.text = film.title

            runningTimeTextView.text = String.format(
                context.resources.getString(R.string.running_time),
                movie.runningTime
            )

            reviewersCountTextView.text = String.format(
                context.resources.getString(R.string.reviews_count),
                movie.reviewCount
            )

            ageRestrictionsTextView.text = String.format(
                context.resources.getString(R.string.age_restrictions),
                movie.pgAge
            )

            genresTextView.text = getGenresString()

            val imgUrlBase: String = "https://image.tmdb.org/t/p/w185" // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg

            Glide.with(itemView.context)
                .load(imgUrlBase + movie.imageUrl)
                .apply(imageOption)
                .into(filmPosterImageView)

            star1.background = if (movie.rating >= 1) context.resources.getDrawable(
                R.drawable.star_icon_on,
                null
            ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

            star2.background = if (movie.rating >= 2) context.resources.getDrawable(
                R.drawable.star_icon_on,
                null
            ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

            star3.background = if (movie.rating >= 3) context.resources.getDrawable(
                R.drawable.star_icon_on,
                null
            ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

            star4.background = if (movie.rating >= 4) context.resources.getDrawable(
                R.drawable.star_icon_on,
                null
            ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

            star5.background = if (movie.rating >= 5) context.resources.getDrawable(
                R.drawable.star_icon_on,
                null
            ) else context.resources.getDrawable(R.drawable.star_icon_off, null)
        }

        private fun getGenresString(): String {
            var genresString: String = ""
            //TODO обработать пустой список genre
            for (genre in movie.genres!!) {
                if (genresString == "") {
                    genresString = genresString.plus(genre?.name)
                } else genresString = genresString.plus(", ").plus(genre?.name)
            }

            return genresString
        }

        companion object {
            private val imageOption = RequestOptions()
                .placeholder(R.drawable.ph_moive_in_list)
                .fallback(R.drawable.ph_moive_in_list)
                .centerCrop()
        }

    }
}

