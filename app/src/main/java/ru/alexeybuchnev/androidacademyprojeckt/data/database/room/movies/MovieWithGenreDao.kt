package ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies

import androidx.room.*
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract

@Dao
interface MovieWithGenreDao {

    @Transaction
    @Query("SELECT * FROM Movies")
    suspend fun getMovieWithGenres(): List<MovieWithGenres>

    //TODO create transaction like https://developer.android.com/reference/kotlin/androidx/room/Transaction

    /*@Insert
    suspend fun insertMovieGenreCrossRef(movieGenreCrossRef: MovieGenreCrossRef)

    @Insert
    suspend fun insertMovieEntity(movieEntity: MovieEntity)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWithGenreRef(movieEntity: MovieEntity, movieGenreCrossRef: List<MovieGenreCrossRef>)
}