package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.R
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MoviesListFragment : Fragment(), MovieAdapter.Callbacks {

    private var callbacks: Callbacks? = null
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var filmsListRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MovieAdapter
    private lateinit var retryButton: Button

    interface Callbacks {
        fun onFilmSelectedClick(movieId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
        adapter = MovieAdapter(callbacks, this as MovieAdapter.Callbacks)
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
        filmsListRecyclerView.adapter = adapter

        movieListViewModel.loadMovies()
        movieListViewModel.stateLiveData.observe(this.viewLifecycleOwner) {
            when (it) {
                is MovieListViewModel.State.Loading -> {
                    setLoading(true)
                    retryButton.visibility = View.GONE
                }
                is MovieListViewModel.State.Success -> {
                    setLoading(false)
                    retryButton.visibility = View.GONE
                }
                is MovieListViewModel.State.Error -> {
                    setLoading(false)
                    retryButton.visibility = View.VISIBLE
                }
            }
        }
        movieListViewModel.movieListLiveData.observe(this.viewLifecycleOwner) {
            updateFilmList(it)
        }

        retryButton.setOnClickListener { movieListViewModel.loadMovies() }
    }

    override fun onListEnded() {
        Log.d("onListEnded", "onListEnded")
        movieListViewModel.loadMovies()
    }

    private fun updateFilmList(moviesList: List<Movie>) {
        adapter.bindMovies(moviesList)
        adapter.notifyDataSetChanged()
    }

    private fun initViews(view: View) {
        filmsListRecyclerView = view.findViewById(R.id.filmsListRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        retryButton = view.findViewById(R.id.retryButton)
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