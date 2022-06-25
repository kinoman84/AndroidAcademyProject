package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.R
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MoviesListFragment : Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var filmsListRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
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

        initViews(view)

        //TODO delete it after implement network
        movieRepository = JsonMovieRepository(requireContext())
        movieListViewModel = ViewModelProvider(
            this,
            MovieListViewModelFactory(movieRepository)
        )[MovieListViewModel::class.java]

        filmsListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        movieListViewModel.loadMovies()
        movieListViewModel.stateLiveData.observe(this.viewLifecycleOwner) {
            when (it) {
                is MovieListViewModel.State.Loading -> {
                    setLoading(true)
                }
                is MovieListViewModel.State.Success -> {
                    setLoading(false)
                }
            }
        }
        movieListViewModel.movieListLiveData.observe(this.viewLifecycleOwner) {
            updateFilmList(it)
        }
    }

    private fun updateFilmList(moviesList: List<Movie>) {
        adapter.bindMovies(moviesList)
        filmsListRecyclerView.adapter = adapter
    }

    private fun initViews(view: View) {
        filmsListRecyclerView = view.findViewById(R.id.filmsListRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun setLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressBar.visibility = View.VISIBLE
            }
            false -> {
                progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesListFragment()
    }
}