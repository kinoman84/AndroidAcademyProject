package ru.alexeybuchnev.androidacademyprojeckt.data

import com.android.academy.fundamentals.homework.data.JsonMovie
import com.android.academy.fundamentals.homework.data.JsonMovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    //TODO убрать ключи
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): JsonMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): JsonMovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") id: Int
    ): CreditsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): JsonGenresResponse
}