package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.data.MovieRepository
import kotlinx.coroutines.launch
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MovieListViewModel (private val movieRepository: MovieRepository) : ViewModel() {

    private val mutableMovieList = MutableLiveData<List<Movie>>(emptyList())
    val movieListLiveData: LiveData<List<Movie>> get() = mutableMovieList

    fun loadMovies() {
        viewModelScope.launch {
            val filmsList: List<Movie> = movieRepository.loadMovies()
            mutableMovieList.value = filmsList
        }
    }
}