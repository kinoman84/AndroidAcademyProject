package ru.alexeybuchnev.androidacademyprojeckt.data.database.genres

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexeybuchnev.androidacademyprojeckt.data.database.Contract

@Entity(tableName = Contract.Genres.TABLE_NAME)
data class GenreEntity(

    @PrimaryKey
    @ColumnInfo(name = Contract.Genres.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = Contract.Genres.COLUMN_NAME_NAME)
    val name: String
)