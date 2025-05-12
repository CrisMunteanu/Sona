package de.syntax_institut.androidabschlussprojekt.domain.model


data class MeditationItem(
    val title: String,
    val imageResId: Int,
    val audioFile: String,
    val duration: String,
    var isFavorite: Boolean = false

)