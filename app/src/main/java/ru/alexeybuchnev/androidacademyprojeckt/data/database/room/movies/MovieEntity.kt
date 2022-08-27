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

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_STORY_LINE)
    val storyLine: String,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_IMAGE_URL)
    val imageUrl: String,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_DETAIL_IMAGE_URL)
    val detailImageUrl: String?,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_RATING)
    val rating: Int,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_REVIEW_COUNT)
    val reviewCount: Int,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_PG_AGE)
    val pgAge: Int,

    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_RUNTIME)
    val runningTime: Int?
)