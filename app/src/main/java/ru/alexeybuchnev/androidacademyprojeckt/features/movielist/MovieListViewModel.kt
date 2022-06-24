package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MovieListViewModel (private val movieRepository: MovieRepository) : ViewModel() {

    private val mutableMovieList = MutableLiveData<List<Movie>>(emptyList())
    val movieListLiveData: LiveData<List<Movie>> get() = mutableMovieList

    private val mutableState = MutableLiveData<State>()
    val stateLiveData get() = mutableState


    fun loadMovies() {

        //TODO add loader
        viewModelScope.launch {
            stateLiveData.value = State.Loading()

            val filmsList: List<Movie> = movieRepository.loadMovies()
            mutableMovieList.value = filmsList

            stateLiveData.value = State.Success()
        }

    }

    sealed class State {
        class Loading : State()
        class Success : State()
    }
}