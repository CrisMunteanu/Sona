package de.syntax_institut.androidabschlussprojekt.data.remote



import de.syntax_institut.androidabschlussprojekt.data.remote.dto.PixabayMusicResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayMusicApiService {
    @GET("/api/audio/")
    suspend fun getMeditationMusic(
        @Query("key") apiKey: String,
        @Query("q") query: String = "meditation",
        @Query("category") category: String = "music",
        @Query("editors_choice") editorsChoice: Boolean = true,
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): PixabayMusicResponse

}