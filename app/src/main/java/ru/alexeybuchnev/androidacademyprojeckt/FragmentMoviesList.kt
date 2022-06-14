package ru.alexeybuchnev.androidacademyprojeckt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import com.android.academy.fundamentals.homework.data.MovieRepository
import kotlinx.coroutines.*
import ru.alexeybuchnev.androidacademyprojeckt.model.Movie

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesList.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var callbacks: Callbacks? = null

    private var scope = CoroutineScope(Job() + Dispatchers.Default)
    private lateinit var filmsListRecyclerView: RecyclerView

    interface Callbacks {
        fun onFilmSelectedClick(movieId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_movies_list, container, false)

        //TODO перенести клик листенер в адаптер FilmsAdapter
        /*view.findViewById<CardView>(R.id.filmItemCardView).apply {
            setOnClickListener { _ ->
                callbacks?.onFilmSelectedClick("filmName")
            }
        }*/
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        filmsListRecyclerView = view.findViewById(R.id.filmsListRecyclerView)

        val movieRepository: MovieRepository = JsonMovieRepository(requireContext())


        scope.launch {
            var filmsList: List<Movie>
            filmsList = movieRepository.loadMovies()
            updateFilmList(filmsList)
        }

        filmsListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


    }

    private suspend fun updateFilmList(moviesList: List<Movie>) = withContext(Dispatchers.Main){
        filmsListRecyclerView.adapter = FilmsAdapter(callbacks).apply { bindMovies(moviesList) }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMoviesList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMoviesList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}