package ru.alexeybuchnev.androidacademyprojeckt.model

data class Movie(
    var id: Int,
    var title: String,
    var storyLine: String,
    var imageUrl: String,
    var detailImageUrl: String? = null,
    var rating: Int,
    var reviewCount: Int,
    var pgAge: Int,
    var runningTime: Int,
    var genres: List<Genre>,
    var actors: List<Actor>? = null,
    var isLiked: Boolean
) {
}