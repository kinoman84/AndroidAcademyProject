package ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreEntity

data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = Contract.Movie.COLUMN_NAME_ID,
        entityColumn = Contract.Genres.COLUMN_NAME_ID,
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)