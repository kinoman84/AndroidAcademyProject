package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.Response
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class MovieListViewModel (private val movieRepository: MovieRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler {
            _, throwable ->
        Log.d("exceptionHandler", throwable.toString())
        mutableState.value = State.Error()

    }

    private val mutableMovieList = MutableLiveData<List<Movie>>(emptyList())
    val movieListLiveData: LiveData<List<Movie>> get() = mutableMovieList

    private val mutableState = MutableLiveData<State>()
    val stateLiveData get() = mutableState


    fun loadMovies() {

        //TODO add loader
        viewModelScope.launch(exceptionHandler) {
            stateLiveData.value = State.Loading()

            val filmsList: List<Movie> = movieRepository.loadMovies()
            mutableMovieList.value = filmsList

            stateLiveData.value = State.Success()
        }

    }

    sealed class State {
        class Loading : State()
        class Success : State()
        class Error : State()
    }
}