package ru.alexeybuchnev.androidacademyprojeckt.data.network

import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre

interface NetworkMovieRepository: MovieRepository {

    suspend fun getGenre(id: Int): Genre?
    suspend fun loadGenres(): List<Genre>
}