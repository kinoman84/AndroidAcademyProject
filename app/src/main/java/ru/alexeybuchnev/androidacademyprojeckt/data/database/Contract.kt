package ru.alexeybuchnev.androidacademyprojeckt.data.database

import android.provider.BaseColumns

object Contract {
    const val DATABASE_NAME = "Movies.db"

    object Genres {
        const val TABLE_NAME = "Genres"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "name"
    }

    object Film {
        const val TABLE_NAME = "Films"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_POSTER_PATH = "poster_path"
        const val COLUMN_NAME_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_NAME_VOTE_AVERAGE = "vote_average"
        const val COLUMN_NAME_VOTE_COUNT = "vote_count"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_ADULT = "adult"
    }
}