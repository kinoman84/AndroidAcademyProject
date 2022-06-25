package ru.alexeybuchnev.androidacademyprojeckt.features.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository

/**
 * Через фабрику можно передать параметры в создаваемую viewholder
 */

class MovieListViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieListViewModel(movieRepository) as T
    }
}