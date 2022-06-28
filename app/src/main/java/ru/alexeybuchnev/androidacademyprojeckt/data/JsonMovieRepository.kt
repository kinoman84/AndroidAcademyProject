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
import ru.alexeybuchnev.androidacademyprojeckt.data.*
import ru.alexeybuchnev.androidacademyprojeckt.model.Actor
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie
import java.util.concurrent.TimeUnit

class JsonMovieRepository(private val context: Context) : MovieRepository {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    private var movies: List<Movie> = emptyList()
    private var page: Int = 1
    private var genresMapCash: Map<Int, Genre>? = null

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val moviesFromJson = loadMoviesFromApi(page)
        page++
        movies = movies.plus(moviesFromJson)
        movies
    }

    private suspend fun loadMoviesFromApi(page: Int): List<Movie> {

        val response: JsonMovieResponse = RetrofitModule.movieApi.getPopularMovies(page)

        val genresMap = loadGenresMap()

        val movieList = response.results?.map { jsonMovie ->

            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                storyLine = jsonMovie.overview,
                imageUrl = jsonMovie.posterPicture,
                //detailImageUrl = jsonMovie.backdropPicture,
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

    private suspend fun loadMovieFromApi(id: Int): Movie {

        val responseMove: JsonMovieDetails = RetrofitModule.movieApi.getMovieDetails(id)
        val casts = loadCredits(id)


        val movie: Movie = Movie(
            id = responseMove.id,
            title = responseMove.title,
            storyLine = responseMove.overview,
            imageUrl = responseMove.posterPicture,
            detailImageUrl = responseMove.backdropPicture,
            rating = (responseMove.ratings / 2).toInt(),
            reviewCount = responseMove.votesCount,
            pgAge = if (responseMove.adult) 16 else 13,
            runningTime = responseMove.runtime?.toInt() ?: 0,
            genres = responseMove.genreIds.map {
                jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name)
            },
            //TODO ограничить список до 10 актёров. убрать актёров без фото или сделать плэйсхолдер
            actors = casts.map { castItem ->
                Actor(
                    id = castItem.id,
                    name = castItem.name,
                    imageUrl = castItem.profilePath ?: "null"
                )},
            isLiked = false
        )

        return movie

    }

    private suspend fun loadCredits(movieId: Int): List<CastItem> {
        return RetrofitModule.movieApi.getCredits(movieId).cast.orEmpty()
    }

    private suspend fun loadMoviesFromJsonFile(): List<Movie> {
        val genresMap = loadGenres()
        val actorsMap = loadActors()

        val data = readAssetFileToString("data.json")
        return parseMovies(data, genresMap, actorsMap)
    }

    private suspend fun loadGenresMap(): Map<Int, Genre> = withContext(Dispatchers.IO) {

        if (genresMapCash != null) {
            genresMapCash as Map<Int, Genre>
        } else {
            val genresResponse: JsonGenresResponse = RetrofitModule.movieApi.getGenres()
            val genres: List<Genre> = genresResponse.genres.map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }
            //val genres : List<Genre> = if (genresCash.isEmpty()) {genresCash} else {genresCash
            val genresMap = genres.associateBy(Genre::id)
            genresMapCash = genresMap
            genresMap
        }
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
//                detailImageUrl = jsonMovie.backdropPicture,
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
        /*val cachedMovies = movies ?: loadMovies()
        return cachedMovies.find { it.id == movieId }*/
        return loadMovieFromApi(movieId)
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
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
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