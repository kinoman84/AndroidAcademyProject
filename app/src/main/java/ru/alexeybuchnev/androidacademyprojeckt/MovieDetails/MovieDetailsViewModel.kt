package ru.alexeybuchnev.androidacademyprojeckt.MovieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.data.MovieRepository
import kotlinx.coroutines.launch
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val mutableMovie = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> get() = mutableMovie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            val movie: Movie? = movieRepository.loadMovie(movieId)
            movie?.let { mutableMovie.value = it }
        }
    }
}