package ru.alexeybuchnev.androidacademyprojeckt.data.database

import android.content.Context
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.MoviesDatabase
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieGenreCrossRef
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieWithGenres
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre

class LocalDataSourceImpl(application: Context) : LocalDataStorage {

    private val roomDatabase : MoviesDatabase = MoviesDatabase.createDd(application)

    override suspend fun gatGenres(): List<GenreEntity> {
        return roomDatabase.genreDao.getAllGenres()
    }

    override suspend fun saveGenres(genres: List<GenreEntity>) {
        roomDatabase.genreDao.saveGenres(genres)
    }

    override suspend fun saveMovieWithGenresRef(movieEntity: MovieEntity, movieGenreCrossRef: List<MovieGenreCrossRef>) {
        roomDatabase.movieDao.insertMovieWithGenreRef(movieEntity, movieGenreCrossRef)
    }

    override suspend fun getMoviesWithGenres(): List<MovieWithGenres> {
        return roomDatabase.movieDao.getMovieWithGenres()
    }
}