package ru.alexeybuchnev.androidacademyprojeckt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_FILM_INDEX = "selectedFilmIndex"

class FragmentMoviesDetails : Fragment() {

    private var selectedFilmIndex: Int? = null
    private lateinit var selectedFilm: Film
    private lateinit var filmNameTextView: TextView
    private lateinit var filmPosterImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
            selectedFilmIndex = it.getInt(ARG_FILM_INDEX)
        }

        selectedFilmIndex?.let { it ->
            selectedFilm = Film.films[it]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        filmNameTextView = view.findViewById(R.id.movie_name_text)
        filmNameTextView.text = selectedFilm.name

        filmPosterImageView = view.findViewById(R.id.movie_logo_image)
        filmPosterImageView.setImageResource(selectedFilm.imageResourceId)


        val actorsRecyclerView: RecyclerView = view.findViewById(R.id.actorsRecyclerView)
        actorsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        actorsRecyclerView.adapter = ActorAdapter(selectedFilm.actors)
        actorsRecyclerView.setHasFixedSize(true)
    }

    companion object {
        @JvmStatic
        fun newInstance(filmIndex: Int) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FILM_INDEX, filmIndex)
                }
            }
    }
}