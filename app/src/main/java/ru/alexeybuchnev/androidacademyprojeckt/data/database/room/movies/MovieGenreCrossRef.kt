package ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract

@Entity(primaryKeys = [Contract.Movie.COLUMN_NAME_ID, Contract.Genres.COLUMN_NAME_ID])
data class MovieGenreCrossRef(
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ID)
    val movieId: Int,
    @ColumnInfo(name = Contract.Genres.COLUMN_NAME_ID)
    val genreId: Int
)