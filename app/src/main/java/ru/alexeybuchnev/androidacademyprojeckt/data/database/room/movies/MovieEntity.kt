package ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract

@Entity(tableName = Contract.Movie.TABLE_NAME)
data class MovieEntity(

    @PrimaryKey
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_POSTER_PATH)
    val posterPath: String,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_BACKDROP_PATH)
    val backdropPath: String?,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_RUNTIME)
    val runtime: Int?,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_RATING)
    val rating: Int,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_VOTE_COUNT)
    val voteCount: Int,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_PG_AGE)
    val pgAge: Int


)


/*

class MovieListItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("adult")
    val adult: Boolean
)

 */