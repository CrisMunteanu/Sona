package de.syntax_institut.androidabschlussprojekt.domain.util



import de.syntax_institut.androidabschlussprojekt.data.remote.dto.NasaApodDto
import de.syntax_institut.androidabschlussprojekt.domain.model.CosmicMeditationApod
import de.syntax_institut.androidabschlussprojekt.domain.model.NasaPicture

fun NasaPicture.toCosmicMeditationApod(): CosmicMeditationApod {
    return CosmicMeditationApod(
        title = title,
        explanation = explanation,
        imageUrl = url,
        date = date,
        audioFile = "space_chime.mp3"
    )
}

fun NasaApodDto.toNasaPicture(): NasaPicture {
    return NasaPicture(
        title = title,
        explanation = explanation,
        url = url,
        date = date
    )
}