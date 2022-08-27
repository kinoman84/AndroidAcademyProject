package ru.alexeybuchnev.androidacademyprojeckt.data.network

import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.GenreListItemResponse
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.MovieDetailsResponse
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.MovieListItemResponse
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

interface NetworkDataSource {
    suspend fun loadMovies(page: Int): List<MovieListItemResponse>
    suspend fun loadMovieDetails(movieId: Int): MovieDetailsResponse
    suspend fun loadGenres(): List<GenreListItemResponse>
}