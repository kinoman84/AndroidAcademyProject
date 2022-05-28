package ru.alexeybuchnev.androidacademyprojeckt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), FragmentMoviesList.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO если переменную понадобится вынести, нужно будет её проинициализировать после переворота экрана
        if (savedInstanceState == null) {
            val fragmentMovieList = FragmentMoviesList.newInstance("param1", "param2")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentMovieList)
                .commit()
        }
    }

    override fun onFilmSelectedClick(filmName: String) {
        val fragmentMoviesDetails = FragmentMoviesDetails.newInstance("","")
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragmentMoviesDetails)
            .addToBackStack(null)
            .commit()
    }
}