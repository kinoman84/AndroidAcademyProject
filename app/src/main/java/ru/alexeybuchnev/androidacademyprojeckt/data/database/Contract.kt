package ru.alexeybuchnev.androidacademyprojeckt.data.database

import android.provider.BaseColumns

object Contract {
    const val DATABASE_NAME = "Movies.db"

    object Genres {
        const val TABLE_NAME = "Genres"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "Name"
    }
}