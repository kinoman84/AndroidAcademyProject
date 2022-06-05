package ru.alexeybuchnev.androidacademyprojeckt

data class Actor (val name: String, val imageResourceId: Int) {
    companion object {
        val actorsMap: Map<String, Actor> = mapOf(
            "robert_downey_jr" to Actor("Robert Downey Jr.", R.drawable.actor_img_robert_downey_jr),
            "chris_evans" to Actor("Chris Evans", R.drawable.actor_img_chris_evans),
            "mark_ruffalo" to Actor("Mark Ruffalo", R.drawable.actor_img_mark_ruffalo),
            "chris_hemsworth" to Actor("Chris Hemsworth", R.drawable.actor_img_chris_hemsworth),
        )
    }
}