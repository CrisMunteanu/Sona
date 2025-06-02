package de.syntax_institut.androidabschlussprojekt.data.remote.dto



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PixabayMusicResponse(
    @SerialName("totalHits") val totalHits: Int,
    @SerialName("hits") val hits: List<PixabayMusicItem>
)

@Serializable
data class PixabayMusicItem(
    val id: Int,
    val user: String,
    @SerialName("userImageURL") val userImageUrl: String,
    val tags: String,
    @SerialName("audio") val audioUrl: String,
    val downloads: Int,
    val likes: Int,
    @SerialName("duration") val durationSec: Int
)