package de.syntax_institut.androidabschlussprojekt.data.remote.dto





import de.syntax_institut.androidabschlussprojekt.domain.model.PixabayMusicTrack

fun PixabayMusicItem.toPixabayTrack(): PixabayMusicTrack {
    return PixabayMusicTrack(
        id = this.id,
        title = this.tags,
        artist = this.user,
        audioUrl = this.audioUrl,
        coverUrl = this.userImageUrl,
        duration = this.durationSec
    )
}