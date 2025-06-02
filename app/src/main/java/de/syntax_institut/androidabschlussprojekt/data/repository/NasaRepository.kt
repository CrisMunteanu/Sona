package de.syntax_institut.androidabschlussprojekt.data.repository


import de.syntax_institut.androidabschlussprojekt.domain.model.NasaPicture

interface NasaRepository {
    suspend fun getPictureOfTheDay(): NasaPicture
}