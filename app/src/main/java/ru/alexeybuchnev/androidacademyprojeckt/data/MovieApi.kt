package ru.alexeybuchnev.androidacademyprojeckt.data

import com.android.academy.fundamentals.homework.data.JsonMovie
import com.android.academy.fundamentals.homework.data.JsonMovieDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    //TODO убрать ключи
    @GET("movie/popular?api_key=e78f70bf9b2c2d41f9e21e9a48553feb&language=en-US&page=1")
    suspend fun getPopularMovies(): JsonMovieResponse

    @GET("movie/{movie_id}?api_key=e78f70bf9b2c2d41f9e21e9a48553feb&language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): JsonMovieDetails

    @GET("movie/{movie_id}/credits?api_key=e78f70bf9b2c2d41f9e21e9a48553feb&language=en-US")
    suspend fun getCredits(
        @Path("movie_id") id: Int
    ): CreditsResponse

    @GET("genre/movie/list?api_key=e78f70bf9b2c2d41f9e21e9a48553feb&language=en-US")
    suspend fun getGenres(): JsonGenresResponse
}