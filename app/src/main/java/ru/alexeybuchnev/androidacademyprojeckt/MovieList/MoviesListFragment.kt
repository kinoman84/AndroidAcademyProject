package ru.alexeybuchnev.androidacademyprojeckt.MovieList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import com.android.academy.fundamentals.homework.data.MovieRepository
import kotlinx.coroutines.*
import ru.alexeybuchnev.androidacademyprojeckt.R
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MoviesListFragment : Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieListViewModel: MovieListViewModel

    private var scope = CoroutineScope(Job() + Dispatchers.Default)
    private lateinit var filmsListRecyclerView: RecyclerView

    private lateinit var adapter: MovieAdapter

    interface Callbacks {
        fun onFilmSelectedClick(movieId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
        adapter = MovieAdapter(callbacks)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        movieRepository = JsonMovieRepository(requireContext())
        movieListViewModel = MovieListViewModel(movieRepository)

        filmsListRecyclerView = view.findViewById(R.id.filmsListRecyclerView)
        filmsListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        movieListViewModel.loadMovies()
        movieListViewModel.movieListLiveData.observe(this.viewLifecycleOwner) {
            updateFilmList(it)
        }

        /*val movieRepository: MovieRepository = JsonMovieRepository(requireContext())

        scope.launch {
            var filmsList: List<Movie>
            filmsList = movieRepository.loadMovies()
            updateFilmList(filmsList)
        }*/


    }

    /*private suspend fun updateFilmList(moviesList: List<Movie>) = withContext(Dispatchers.Main){
        filmsListRecyclerView.adapter = MovieAdapter(callbacks).apply { bindMovies(moviesList) }
    }*/

    private fun updateFilmList(moviesList: List<Movie>) {
        adapter.bindMovies(moviesList)
        filmsListRecyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesListFragment()
    }
}