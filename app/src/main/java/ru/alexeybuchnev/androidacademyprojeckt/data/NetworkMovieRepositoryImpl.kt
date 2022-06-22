package ru.alexeybuchnev.androidacademyprojeckt.data

import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class NetworkMovieRepositoryImpl: MovieRepository {


    override suspend fun loadMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun loadMovie(movieId: Int): Movie? {
        TODO("Not yet implemented")
    }
}