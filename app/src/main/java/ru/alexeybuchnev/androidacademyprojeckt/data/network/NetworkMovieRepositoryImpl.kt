package ru.alexeybuchnev.androidacademyprojeckt.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.alexeybuchnev.androidacademyprojeckt.BuildConfig
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.*
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie
import java.util.concurrent.TimeUnit

class NetworkMovieRepositoryImpl: NetworkDataSource {

    override suspend fun loadMovies(page: Int): List<MovieListItemResponse> {
        val response: MovieListResponse = RetrofitModule.movieApi.getPopularMovies(page)
        return response.results.orEmpty()
    }

    override suspend fun loadMovieDetails(movieId: Int): MovieDetailsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun loadGenres(): List<GenreListItemResponse> = withContext(Dispatchers.IO) {
        val genresResponse: GenresListResponse = RetrofitModule.movieApi.getGenres()
        genresResponse.genres
    }
}

private object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val client: OkHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(AddQueryParamInterceptor("api_key", BuildConfig.apiKey))
        .addInterceptor(AddQueryParamInterceptor("language", "en-US"))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieApi: MovieApi = retrofit.create()
}

class AddQueryParamInterceptor(val name: String, val value: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin: Request = chain.request()
        val request = origin.newBuilder()
            .url(
                origin.url.newBuilder()
                    .addQueryParameter(name, value).build()
            )
            .build()

        return chain.proceed(request)
    }
}