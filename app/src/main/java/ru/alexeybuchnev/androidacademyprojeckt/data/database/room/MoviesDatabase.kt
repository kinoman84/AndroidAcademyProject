package ru.alexeybuchnev.androidacademyprojeckt.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreDao
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieGenreCrossRef
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieWithGenreDao

/**
 * https://developer.android.com/training/data-storage/room
 */

@Database(
    entities = [GenreEntity::class, MovieEntity::class, MovieGenreCrossRef::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val genreDao: GenreDao

    abstract val movieDao: MovieWithGenreDao

    companion object {
        fun createDd(application: Context): MoviesDatabase {
            return Room.databaseBuilder(
                application,
                MoviesDatabase::class.java,
                Contract.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}