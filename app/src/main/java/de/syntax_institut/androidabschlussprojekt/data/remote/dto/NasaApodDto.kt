package de.syntax_institut.androidabschlussprojekt.data.remote.dto

import de.syntax_institut.androidabschlussprojekt.domain.model.NasaPicture


data class NasaApodDto(
    val title: String,
    val explanation: String,
    val url: String,
    val date: String
)

fun NasaApodDto.toNasaPicture(): NasaPicture {
    return NasaPicture(
        title = title,
        explanation = explanation,
        url = url,
        date = date
    )
}