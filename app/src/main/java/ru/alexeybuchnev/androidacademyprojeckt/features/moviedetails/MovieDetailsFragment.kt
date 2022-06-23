package ru.alexeybuchnev.androidacademyprojeckt.features.moviedetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import com.bumptech.glide.Glide
import ru.alexeybuchnev.androidacademyprojeckt.ActorAdapter
import ru.alexeybuchnev.androidacademyprojeckt.R
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

private const val ARG_FILM_INDEX = "selectedMovieId"

class FragmentMoviesDetails : Fragment() {

    private var selectedMovieId: Int? = null
    private lateinit var movieViewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieRepository

    private lateinit var filmNameTextView: TextView
    private lateinit var ageRestrictionTextView: TextView
    private lateinit var genresTextView: TextView
    private lateinit var storylineTextView: TextView
    private lateinit var reviewersTextView: TextView
    private lateinit var castTitleTextView: TextView
    private lateinit var filmPosterImageView: ImageView
    private lateinit var actorsRecyclerView: RecyclerView

    private lateinit var star1: View
    private lateinit var star2: View
    private lateinit var star3: View
    private lateinit var star4: View
    private lateinit var star5: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
            selectedMovieId = it.getInt(ARG_FILM_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val context: Context = requireContext()
        //TODO delete it after implement network
        movieRepository = JsonMovieRepository(context)
        movieViewModel = MovieDetailsViewModel(movieRepository)

        initView(view)

        actorsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        movieViewModel.movieLiveData.observe(this.viewLifecycleOwner) {
            updateUi(it)
        }

        selectedMovieId?.let { movieViewModel.loadMovie(it) }

    }

    private fun initView(view: View) {
        filmNameTextView = view.findViewById(R.id.movie_name_text)
        filmPosterImageView = view.findViewById(R.id.film_logo_image_view)
        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView)
        ageRestrictionTextView = view.findViewById(R.id.age_restrictions_text_view)
        genresTextView = view.findViewById(R.id.genres_text_view)
        storylineTextView = view.findViewById(R.id.storyline_text_view)
        reviewersTextView = view.findViewById(R.id.reviewers_count_text_view)
        castTitleTextView = view.findViewById(R.id.cast_title_text_view)

        star1 = view.findViewById(R.id.star1)
        star2 = view.findViewById(R.id.star2)
        star3 = view.findViewById(R.id.star3)
        star4 = view.findViewById(R.id.star4)
        star5 = view.findViewById(R.id.star5)
    }

    private fun updateUi(selectedMovie: Movie) {

        val context: Context = requireContext()

        filmNameTextView.text = selectedMovie.title

        Glide.with(requireContext())
            .load(selectedMovie.detailImageUrl)
            .into(filmPosterImageView)

        ageRestrictionTextView.text = String.format(
            context.resources.getString(R.string.age_restrictions),
            selectedMovie.pgAge
        )

        reviewersTextView.text = String.format(
            context.resources.getString(R.string.reviews_count),
            selectedMovie.reviewCount
        )

        genresTextView.text = getGenresString(selectedMovie)
        storylineTextView.text = selectedMovie.storyLine

        if (selectedMovie.actors?.isEmpty() == true) castTitleTextView.visibility = View.GONE


        actorsRecyclerView.adapter = ActorAdapter(selectedMovie.actors.orEmpty())

        star1.background = if (selectedMovie.rating >= 1) context.resources.getDrawable(
            R.drawable.star_icon_on,
            null
        ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

        star2.background = if (selectedMovie.rating >= 2) context.resources.getDrawable(
            R.drawable.star_icon_on,
            null
        ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

        star3.background = if (selectedMovie.rating >= 3) context.resources.getDrawable(
            R.drawable.star_icon_on,
            null
        ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

        star4.background = if (selectedMovie.rating >= 4) context.resources.getDrawable(
            R.drawable.star_icon_on,
            null
        ) else context.resources.getDrawable(R.drawable.star_icon_off, null)

        star5.background = if (selectedMovie.rating >= 5) context.resources.getDrawable(
            R.drawable.star_icon_on,
            null
        ) else context.resources.getDrawable(R.drawable.star_icon_off, null)
    }

    private fun getGenresString(selectedMovie: Movie): String {
        var genresString: String = ""

        for (genre in selectedMovie.genres) {
            if (genresString == "") {
                genresString = genresString.plus(genre.name)
            } else genresString = genresString.plus(", ").plus(genre.name)
        }

        return genresString
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FILM_INDEX, movieId)
                }
            }
    }
}