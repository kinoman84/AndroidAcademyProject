package ru.alexeybuchnev.androidacademyprojeckt.features.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository

/**
 * Через фабрику можно передать параметры в создаваемую viewholder
 */
class MovieDetailsViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieRepository) as T
    }
}