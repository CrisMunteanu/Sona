package de.syntax_institut.androidabschlussprojekt.data.repository


import android.util.Log
import de.syntax_institut.androidabschlussprojekt.data.remote.dto.PixabayMusicItem
import de.syntax_institut.androidabschlussprojekt.data.remote.PixabayMusicApiService


class PixabayMusicRepository(
    private val pixabayMusicApiService: PixabayMusicApiService,
    private val apiKey: String
) {
    suspend fun getMeditationTracks(page: Int = 1): List<PixabayMusicItem> {
        return try {
            val response = pixabayMusicApiService.getMeditationMusic(apiKey = apiKey, page = page)
            Log.d("PixabayAPI", "Erfolg! ${response.hits.size} Elemente geladen.")
            response.hits
        } catch (e: Exception) {
            Log.e("PixabayAPI", "Fehler beim Laden der Tracks", e)
            emptyList()
        }
    }
}