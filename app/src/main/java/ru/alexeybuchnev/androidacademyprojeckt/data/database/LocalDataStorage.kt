package ru.alexeybuchnev.androidacademyprojeckt.data.database

import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieGenreCrossRef
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieWithGenres
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

interface LocalDataStorage {
    suspend fun gatGenres(): List<GenreEntity>
    suspend fun saveGenres(genres: List<GenreEntity>)
    suspend fun saveMovieWithGenresRef(movieEntity: MovieEntity, movieGenreCrossRef: List<MovieGenreCrossRef>)
    suspend fun getMoviesWithGenres(): List<MovieWithGenres>
}