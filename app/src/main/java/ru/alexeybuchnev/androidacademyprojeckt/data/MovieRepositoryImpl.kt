package com.android.academy.fundamentals.homework.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
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
import ru.alexeybuchnev.androidacademyprojeckt.data.*
import ru.alexeybuchnev.androidacademyprojeckt.data.database.LocalDataSourceImpl
import ru.alexeybuchnev.androidacademyprojeckt.data.database.LocalDataStorage
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.genres.GenreEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieEntity
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieGenreCrossRef
import ru.alexeybuchnev.androidacademyprojeckt.data.database.room.movies.MovieWithGenres
import ru.alexeybuchnev.androidacademyprojeckt.data.network.MovieApi
import ru.alexeybuchnev.androidacademyprojeckt.data.network.NetworkDataSource
import ru.alexeybuchnev.androidacademyprojeckt.data.network.NetworkMovieRepositoryImpl
import ru.alexeybuchnev.androidacademyprojeckt.data.network.models.*
import ru.alexeybuchnev.androidacademyprojeckt.model.Actor
import ru.alexeybuchnev.androidacademyprojeckt.model.Genre
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie
import java.util.concurrent.TimeUnit

/**
 * прочитать:https://habr.com/ru/post/456256/
 */

class MovieRepositoryImpl(private val context: Context) : MovieRepository {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    private var movies: List<Movie> = emptyList()
    private var page: Int = 1
    private var genresMapCash: Map<Int, Genre>? = null

    private val networkRepository: NetworkDataSource = NetworkMovieRepositoryImpl()
    private val movieDatabase: LocalDataStorage = LocalDataSourceImpl(context)

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        /*val moviesFromJson = loadMoviesFromApi(page)
        page++
        movies = movies.plus(moviesFromJson)
        movies*/
        //TODO вернуть пагинацию
        //TODO надовозвращать лайф дату наверное. тогда сразу можно вернуть кэш и после обновления, обновить UI
        val genres = loadGenres()
        var movieEntityList = movieDatabase.getMoviesWithGenres()

        if (movieEntityList.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                val networkList = networkRepository.loadMovies()
                val moviesList = networkList.map { toMovie(it, genres) }

                for (movie in moviesList) {
                    saveMovieWithGenreRef(movie)
                }
            }

            movieEntityList.map { toMovie(it) }
        } else {
            val networkList = networkRepository.loadMovies()
            val moviesList = networkList.map { toMovie(it, genres) }

            for (movie in moviesList) {
                saveMovieWithGenreRef(movie)
            }
            moviesList
        }

/*        if (movieEntityList.isEmpty()) {
            val networkList = networkRepository.loadMovies()
            val moviesList = networkList.map { toMovie(it, genres) }

            for (movie in moviesList) {
                saveMovieWithGenreRef(movie)
            }

            movieEntityList = movieDatabase.getMoviesWithGenres()
        }
        movieEntityList.map { toMovie(it) }*/
    }

    private fun toMovie(movieEntityWithGenres: MovieWithGenres): Movie {
        return Movie(
            id = movieEntityWithGenres.movie.id,
            title = movieEntityWithGenres.movie.title,
            storyLine = movieEntityWithGenres.movie.overview,
            imageUrl = movieEntityWithGenres.movie.posterPath,
            rating = movieEntityWithGenres.movie.rating,
            reviewCount = movieEntityWithGenres.movie.voteCount,
            pgAge = movieEntityWithGenres.movie.pgAge,
            runningTime = movieEntityWithGenres.movie.runtime ?: 0,
            genres = movieEntityWithGenres.genres.map { Genre(it.id, it.name) },
            detailImageUrl = null,
            isLiked = false
        )
    }

    private suspend fun saveMovieWithGenreRef(movie: Movie) {
        val genreRefsList = movie.genres.map { MovieGenreCrossRef(movie.id, it.id) }
        val movieEntity = MovieEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.imageUrl,
            backdropPath = null,
            runtime = movie.runningTime,
            rating = movie.rating,
            voteCount = movie.reviewCount,
            overview = movie.storyLine,
            pgAge = movie.pgAge
        )
        movieDatabase.saveMovieWithGenresRef(movieEntity, genreRefsList)
    }

    private suspend fun loadMovieFromApi(id: Int): Movie {

        val responseMove: MovieDetailsResponse = RetrofitModule.movieApi.getMovieDetails(id)
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
            genres = responseMove.genreIds.map { jsonGenre ->
                Genre(id = jsonGenre.id, name = jsonGenre.name)
            },
            //TODO ограничить список до 10 актёров. убрать актёров без фото или сделать плэйсхолдер
            actors = casts.map { castItem ->
                Actor(
                    id = castItem.id,
                    name = castItem.name,
                    imageUrl = castItem.profilePath ?: "null"
                )
            },
            isLiked = false
        )

        return movie

    }

    private suspend fun loadCredits(movieId: Int): List<CreditsItemResponse> {
        return RetrofitModule.movieApi.getCredits(movieId).cast.orEmpty()
    }

    private suspend fun loadGenresMap(): Map<Int, Genre> = withContext(Dispatchers.IO) {

        if (genresMapCash != null) {
            genresMapCash as Map<Int, Genre>
        } else {
            val genresResponse: GenresListResponse = RetrofitModule.movieApi.getGenres()
            val genres: List<Genre> = genresResponse.genres.map { jsonGenre ->
                Genre(
                    id = jsonGenre.id,
                    name = jsonGenre.name
                )
            }
            //val genres : List<Genre> = if (genresCash.isEmpty()) {genresCash} else {genresCash
            val genresMap = genres.associateBy(Genre::id)
            genresMapCash = genresMap
            genresMap
        }
    }

    private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {

        var genreList: List<Genre> = movieDatabase.gatGenres().map { toGenre(it) }
        if (genreList.isEmpty()) {
            val genresFromApi = networkRepository.loadGenres().map { toGenre(it) }
            genreList = genresFromApi
            movieDatabase.saveGenres(genresFromApi.map { toGenreEntity(it) })
        }
        genreList
    }

    private fun readAssetFileToString(fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }

    private suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString("people.json")
        val jsonActors = jsonFormat.decodeFromString<List<CreditsItemResponse>>(data)

        jsonActors.map { jsonActor ->
            Actor(
                id = jsonActor.id,
                name = jsonActor.name,
                //TODO обработать возможные нулы
                imageUrl = jsonActor.profilePath ?: "null"
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

        val jsonMovies = jsonFormat.decodeFromString<List<MovieListItemResponse>>(jsonString)

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
                actors = listOf(Actor(1, "name", "url")),
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

    /**
     * мапперы
     */

    private fun toGenre(entity: GenreEntity): Genre {
        return Genre(id = entity.id, name = entity.name)
    }

    private fun toGenreEntity(genre: Genre): GenreEntity {
        return GenreEntity(id = genre.id, name = genre.name)
    }

    private fun toGenre(genreResponse: GenreListItemResponse): Genre {
        return Genre(id = genreResponse.id, name = genreResponse.name)
    }

    private fun toMovie(movieItemResponse: MovieListItemResponse, genres: List<Genre>): Movie {

        val filmGenres: List<Genre> = movieItemResponse.genreIds.map { id ->
            genres.find { it.id == id }.orThrow { IllegalArgumentException("Genre not found") }
        }

        return Movie(
            id = movieItemResponse.id,
            title = movieItemResponse.title,
            storyLine = movieItemResponse.overview,
            imageUrl = movieItemResponse.posterPicture,
            rating = (movieItemResponse.ratings / 2).toInt(),
            reviewCount = movieItemResponse.votesCount,
            pgAge = if (movieItemResponse.adult) 16 else 13,
            genres = filmGenres,
            actors = emptyList(),
            runningTime = 0,
            isLiked = false
        )
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