package ru.alexeybuchnev.androidacademyprojeckt.data

import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}