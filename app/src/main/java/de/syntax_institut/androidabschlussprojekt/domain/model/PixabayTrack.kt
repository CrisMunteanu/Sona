package de.syntax_institut.androidabschlussprojekt.domain.model




data class PixabayMusicTrack(
    val id: Int,
    val title: String,
    val artist: String,
    val audioUrl: String,
    val coverUrl: String,
    val duration: Int
)