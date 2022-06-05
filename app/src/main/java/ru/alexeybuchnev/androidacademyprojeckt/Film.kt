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
                    Actor.actorsMap.getValue("chris_hemsworth"),
                    Actor.actorsMap.getValue("scarlett_johansson")
                )
            ),
            Film(
                "Tenet",
                R.drawable.film_poster_tenet,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                135,
                actors = listOf(
                    Actor.actorsMap.getValue("robert_pattinson"),
                    Actor.actorsMap.getValue("elizabeth_debicki"),
                    Actor.actorsMap.getValue("john_david_washington"),
                    Actor.actorsMap.getValue("kenneth_branagh")
                )
            ),
            Film(
                "Black Widow",
                R.drawable.film_poster_black_widow,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                108,
                actors = listOf(
                    Actor.actorsMap.getValue("scarlett_johansson"),
                    Actor.actorsMap.getValue("florence_pugh"),
                    Actor.actorsMap.getValue("david_harbour"),
                    Actor.actorsMap.getValue("rachel_weisz")
                )
            ),
            Film(
                "Wonder Woman 1984",
                R.drawable.film_poster_wonder_woman_1984,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                117,
                actors = listOf(
                    Actor.actorsMap.getValue("gal_gadot"),
                    Actor.actorsMap.getValue("chris_pine"),
                    Actor.actorsMap.getValue("kristen_wiig"),
                    Actor.actorsMap.getValue("pedro_pascal")
                )
            ),
            Film(
                "The Terminator",
                R.drawable.film_poster_terminator,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                98
            ),
            Film(
                "Rocky",
                R.drawable.film_poster_rocky,
                listOf("Action", "Adventure", "Drama"),
                13,
                4,
                125,
                105
            )
        )
    }
}
