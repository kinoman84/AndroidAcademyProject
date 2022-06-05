package ru.alexeybuchnev.androidacademyprojeckt

data class Film(
    val name: String,
    val imageResourceId: Int,
    val tags: List<String> = listOf(),
    val ageRating: Int,
    var rating: Int = 0,
    var reviewersCount: Int = 0,
    val durationMinutes: Int,
    var isLike: Boolean = false,
    val actors: List<Actor> = listOf()
) {


    companion object {
        val films = listOf(
            Film(
                "Avengers: End Game",
                R.drawable.film_poster_avenges_end_game,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                120,
                actors = listOf(
                    Actor.actorsMap.getValue("robert_downey_jr"),
                    Actor.actorsMap.getValue("chris_evans"),
                    Actor.actorsMap.getValue("mark_ruffalo"),
                    Actor.actorsMap.getValue("chris_hemsworth")
                )
            ),
            Film(
                "Tenet",
                R.drawable.film_poster_avenges_end_game,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                135
            ),
            Film(
                "Black Widow",
                R.drawable.film_poster_avenges_end_game,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                108
            ),
            Film(
                "Wonder Woman 1984",
                R.drawable.film_poster_avenges_end_game,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                117
            ),
            Film(
                "The Terminator",
                R.drawable.film_poster_avenges_end_game,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                98
            ),
            Film(
                "Rambo",
                R.drawable.film_poster_avenges_end_game,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                105
            )
        )
    }
}
