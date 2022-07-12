package ru.alexeybuchnev.androidacademyprojeckt.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreDao
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreEntity

@Database(entities = [GenreEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val genreDao : GenreDao

    companion object {
        fun createDd(application: Context) : MoviesDatabase {
            return Room.databaseBuilder(
                application,
                MoviesDatabase::class.java,
                Contract.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}