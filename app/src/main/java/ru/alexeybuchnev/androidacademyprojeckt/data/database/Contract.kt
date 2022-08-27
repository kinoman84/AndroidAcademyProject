package ru.alexeybuchnev.androidacademyprojeckt.data.database

import android.provider.BaseColumns

object Contract {
    const val DATABASE_NAME = "Movies.db"

    object Genres {
        const val TABLE_NAME = "Genres"

        const val COLUMN_NAME_ID = "genre_id"
        const val COLUMN_NAME_NAME = "name"
    }

    object Movie {
        const val TABLE_NAME = "Movies"

        const val COLUMN_NAME_ID = "movie_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_STORY_LINE = "story_line"
        const val COLUMN_NAME_IMAGE_URL = "image_url"
        const val COLUMN_NAME_DETAIL_IMAGE_URL = "detail_image_url"
        const val COLUMN_NAME_RATING = "rating"
        const val COLUMN_NAME_REVIEW_COUNT = "review_count"
        const val COLUMN_NAME_PG_AGE = "pg_age"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_IS_LIKE = "is_like"
    }
}