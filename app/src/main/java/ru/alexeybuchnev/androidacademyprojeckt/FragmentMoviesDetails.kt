package ru.alexeybuchnev.androidacademyprojeckt

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
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

private const val ARG_FILM_INDEX = "selectedMovieId"

class FragmentMoviesDetails : Fragment() {

    private var selectedMovieId: Int? = null
    private lateinit var selectedMovie: Movie
    private lateinit var filmNameTextView: TextView
    private lateinit var filmPosterImageView: ImageView
    private lateinit var actorsRecyclerView: RecyclerView

    private var scope = CoroutineScope(Job() + Dispatchers.Default)


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val movieRepository: MovieRepository = JsonMovieRepository(requireContext())

        filmNameTextView = view.findViewById(R.id.movie_name_text)
        filmPosterImageView = view.findViewById(R.id.film_logo_image_view)
        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView)

        actorsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        scope.launch {
            selectedMovieId?.let { it ->
                selectedMovie = movieRepository.loadMovie(it)!!
                updateUi()
            }
        }

    }

    private suspend fun updateUi() = withContext(Dispatchers.Main) {

        filmNameTextView.text = selectedMovie.title

        Glide.with(requireContext())
            .load(selectedMovie.detailImageUrl)
            .into(filmPosterImageView)


        actorsRecyclerView.adapter = ActorAdapter(selectedMovie.actors)




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