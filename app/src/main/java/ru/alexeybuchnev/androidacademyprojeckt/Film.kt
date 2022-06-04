package ru.alexeybuchnev.androidacademyprojeckt

data class Film(val name: String) {


    companion object {
        val films = listOf(
            Film("Avengers: End Game"),
            Film("Tenet"),
            Film("Black Widow"),
            Film("Wonder Woman 1984"),
            Film("The Terminator"),
            Film("Rambo"),
        )
    }
}
