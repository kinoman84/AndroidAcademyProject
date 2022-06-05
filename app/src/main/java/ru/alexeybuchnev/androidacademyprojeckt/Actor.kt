package ru.alexeybuchnev.androidacademyprojeckt

data class Actor (val name: String, val imageResourceId: Int) {
    companion object {
        val actorsMap: Map<String, Actor> = mapOf(
            "robert_downey_jr" to Actor("Robert Downey Jr.", R.drawable.actor_img_robert_downey_jr),
            "chris_evans" to Actor("Chris Evans", R.drawable.actor_img_chris_evans),
            "mark_ruffalo" to Actor("Mark Ruffalo", R.drawable.actor_img_mark_ruffalo),
            "chris_hemsworth" to Actor("Chris Hemsworth", R.drawable.actor_img_chris_hemsworth),

            "robert_pattinson" to Actor("Robert Pattinson", R.drawable.actor_img_robert_pattinson),
            "elizabeth_debicki" to Actor("Elizabeth Debicki", R.drawable.actor_img_elizabeth_debicki),
            "john_david_washington" to Actor("John David Washington", R.drawable.actor_img_john_david_washington),
            "kenneth_branagh" to Actor("Kenneth Branagh", R.drawable.actor_img_kenneth_branagh),

            "scarlett_johansson" to Actor("Scarlett Johansson", R.drawable.actor_img_scarlett_johansson),
            "florence_pugh" to Actor("Florence Pugh", R.drawable.actor_img_florence_pugh),
            "david_harbour" to Actor("David Harbour", R.drawable.actor_img_david_harbour),
            "rachel_weisz" to Actor("Rachel Weisz", R.drawable.actor_img_rachel_weisz),

            "gal_gadot" to Actor("Gal Gadot", R.drawable.actor_img_gal_gadot),
            "chris_pine" to Actor("Chris Pine", R.drawable.actor_img_chris_pine),
            "kristen_wiig" to Actor("Kristen Wiig", R.drawable.actor_img_kristen_wiig),
            "pedro_pascal" to Actor("Pedro Pascal", R.drawable.actor_img_pedro_pascal),
        )
    }
}