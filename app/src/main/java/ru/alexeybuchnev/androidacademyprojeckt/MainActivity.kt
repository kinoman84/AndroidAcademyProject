package ru.alexeybuchnev.androidacademyprojeckt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.alexeybuchnev.androidacademyprojeckt.MovieList.MoviesListFragment

class MainActivity : AppCompatActivity(), MoviesListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO если переменную понадобится вынести, нужно будет её проинициализировать после переворота экрана
        if (savedInstanceState == null) {
            val fragmentMovieList = MoviesListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentMovieList)
                .commit()
        }
    }

    override fun onFilmSelectedClick(movieId: Int) {
        val fragmentMoviesDetails = FragmentMoviesDetails.newInstance(movieId)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragmentMoviesDetails)
            .addToBackStack(null)
            .commit()
    }
}