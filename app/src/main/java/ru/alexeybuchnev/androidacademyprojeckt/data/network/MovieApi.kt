package ru.alexeybuchnev.androidacademyprojeckt.data.network

import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.CreditsResponse
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.GenresListResponse
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.MovieListResponse

interface MovieApi {
    //TODO убрать ключи
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") id: Int
    ): CreditsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresListResponse
}