package ru.alexeybuchnev.androidacademyprojeckt.data

import retrofit2.http.GET

interface MovieApi {
    //TODO убрать ключи
    @GET("movie/popular?api_key=e78f70bf9b2c2d41f9e21e9a48553feb&language=en-US&page=1")
    suspend fun getPopularMovies(): JsonMovieResponse

    @GET("genre/movie/list?api_key=e78f70bf9b2c2d41f9e21e9a48553feb&language=en-US")
    suspend fun getGenres(): JsonGenresResponse
}