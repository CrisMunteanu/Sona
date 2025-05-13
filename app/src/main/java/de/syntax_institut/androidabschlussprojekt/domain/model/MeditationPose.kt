package de.syntax_institut.androidabschlussprojekt.domain.model


data class MeditationPose(
    val id: Int,
    val name: String,
    val description: String,
    val imageRes: Int,
    val longDescription: String
)