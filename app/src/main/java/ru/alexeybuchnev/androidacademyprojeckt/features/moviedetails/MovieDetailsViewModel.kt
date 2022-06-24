package ru.alexeybuchnev.androidacademyprojeckt.features.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val mutableMovie = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> get() = mutableMovie

    fun loadMovie(movieId: Int) {
        //TODO add loader
        viewModelScope.launch {
            val movie: Movie? = movieRepository.loadMovie(movieId)
            movie?.let { mutableMovie.value = it }
        }
    }
}