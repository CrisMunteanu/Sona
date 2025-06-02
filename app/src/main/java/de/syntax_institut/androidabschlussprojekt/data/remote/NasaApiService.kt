package de.syntax_institut.androidabschlussprojekt.data.remote



import de.syntax_institut.androidabschlussprojekt.data.remote.dto.NasaApodDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String
    ): NasaApodDto
}