package ru.alexeybuchnev.androidacademyprojeckt.data.database.genres

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {

    @Query("SELECT * FROM Genres")
    suspend fun getAllGenres(): List<GenreEntity>

    @Insert
    suspend fun addGenres(genres: List<GenreEntity>)
}