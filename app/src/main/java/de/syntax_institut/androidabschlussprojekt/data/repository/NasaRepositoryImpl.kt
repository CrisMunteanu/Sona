package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.NasaApiService

import de.syntax_institut.androidabschlussprojekt.domain.model.NasaPicture
import de.syntax_institut.androidabschlussprojekt.domain.util.toNasaPicture


class NasaRepositoryImpl(
    private val apiService: NasaApiService,
    private val apiKey: String
) : NasaRepository {

    override suspend fun getPictureOfTheDay(): NasaPicture {
        return apiService.getPictureOfTheDay(apiKey).toNasaPicture()
    }
}