package ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {

    @Query("SELECT * FROM Genres")
    suspend fun getAllGenres(): List<GenreEntity>

    @Insert
    suspend fun saveGenres(genres: List<GenreEntity>)
}