package de.syntax_institut.androidabschlussprojekt.domain.model



data class CosmicMeditationApod(
    val title: String,
    val explanation: String,
    val imageUrl: String,
    val date: String,
    val audioFile: String
)

fun getRandomSpaceAudioFile(): String {
    val spaceSounds = listOf(
        "space_chime.mp3",
        "galaxy_ambient.mp3",
        "cosmic_flow.mp3",
        "interstellar_drift.mp3",
        "nebula_waves.mp3"
    )
    return spaceSounds.random()
}