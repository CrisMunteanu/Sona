package de.syntax_institut.androidabschlussprojekt.data.remote.dto

import de.syntax_institut.androidabschlussprojekt.domain.model.CosmicMeditationApod
import de.syntax_institut.androidabschlussprojekt.domain.model.NasaPicture
import de.syntax_institut.androidabschlussprojekt.domain.model.getRandomSpaceAudioFile


data class NasaApodDto(
    val title: String,
    val explanation: String,
    val url: String,
    val date: String
)


fun NasaPicture.toCosmicMeditationApod(): CosmicMeditationApod {
    return CosmicMeditationApod(
        title = title,
        explanation = explanation,
        imageUrl = url,
        date = date,
        audioFile = getRandomSpaceAudioFile() // ← hier wird’s verwendet
    )
}