package com.android.academy.fundamentals.homework.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.alexeybuchnev.androidacademyprojeckt.data.JsonGenresResponse
import ru.alexeybuchnev.androidacademyprojeckt.data.JsonMovieResponse
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieApi
import ru.alexeybuchnev.androidacademyprojeckt.data.MovieRepository
import ru.alexeybuchnev.androidacademyprojeckt.model.Actor
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

class JsonMovieRepository(private val context: Context) : MovieRepository {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    private var movies: List<Movie>? = null

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val cachedMovies = movies
        if (cachedMovies != null) {
            cachedMovies
        } else {
            //val moviesFromJson = loadMoviesFromJsonFile()
            val moviesFromJson = loadMoviesFromApi()
            movies = moviesFromJson
            moviesFromJson
        }
    }

    private suspend fun loadMoviesFromApi(): List<Movie> {

        val response: JsonMovieResponse = RetrofitModule.movieApi.getPopularMovies()
        val genresResponse: JsonGenresResponse = RetrofitModule.movieApi.getGenres()


        val genres: List<Genre> = genresResponse.genres.map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }

        val genresMap = genres.associateBy(Genre::id)

        val movieList = response.results?.map { jsonMovie ->

            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                storyLine = jsonMovie.overview,
                imageUrl = jsonMovie.posterPicture,
                detailImageUrl = jsonMovie.backdropPicture,
                rating = (jsonMovie.ratings / 2).toInt(),
                reviewCount = jsonMovie.votesCount,
                pgAge = if (jsonMovie.adult) 16 else 13,
                runningTime = jsonMovie.runtime?.toInt() ?: 0,
                genres = jsonMovie.genreIds.map { id ->
                    genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
                },
                isLiked = false
            )

        }

        return movieList ?: emptyList()

    }

    private suspend fun loadMoviesFromJsonFile(): List<Movie> {
        val genresMap = loadGenres()
        val actorsMap = loadActors()

        val data = readAssetFileToString("data.json")
        return parseMovies(data, genresMap, actorsMap)
    }

    private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString("genres.json")
        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
        jsonGenres.map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }
    }

    private fun readAssetFileToString(fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }

    private suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString("people.json")
        val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)

        jsonActors.map { jsonActor ->
            Actor(
                id = jsonActor.id,
                name = jsonActor.name,
                imageUrl = jsonActor.profilePicture
            )
        }
    }

    private fun parseMovies(
        jsonString: String,
        genreData: List<Genre>,
        actors: List<Actor>
    ): List<Movie> {
        val genresMap = genreData.associateBy(Genre::id)
        val actorsMap = actors.associateBy(Actor::id)

        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(jsonString)

        return jsonMovies.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                storyLine = jsonMovie.overview,
                imageUrl = jsonMovie.posterPicture,
                detailImageUrl = jsonMovie.backdropPicture,
                rating = (jsonMovie.ratings / 2).toInt(),
                reviewCount = jsonMovie.votesCount,
                pgAge = if (jsonMovie.adult) 16 else 13,
                runningTime = 0,
                //runningTime = jsonMovie.runtime,
//                genres = jsonMovie.genreIds.map { id ->
//                    genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
//                },
//                actors = jsonMovie.actors.map { id ->
//                    actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
//                },
                genres = listOf(Genre(1, "name")),
                actors = listOf(Actor(1,"name", "url")),
                isLiked = false
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): Movie? {
        val cachedMovies = movies ?: loadMovies()
        return cachedMovies.find { it.id == movieId }
    }

    private fun <T : Any> T?.orThrow(createThrowable: () -> Throwable): T {
        return this ?: throw createThrowable()
    }
}

private object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val client : OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieApi: MovieApi = retrofit.create()
}