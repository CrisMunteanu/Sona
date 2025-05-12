package de.syntax_institut.androidabschlussprojekt.data.local.mapper

import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteEntity
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem

fun FavoriteEntity.toMeditationItem(): MeditationItem {
    return MeditationItem(
        title = this.title,
        imageResId = this.imageResId,
        audioFile = this.audioFile,
        duration = this.duration
    )
}

fun MeditationItem.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        title = this.title,
        imageResId = this.imageResId,
        audioFile = this.audioFile,
        duration = this.duration
    )
}