package de.syntax_institut.androidabschlussprojekt.data.local.mapper



import de.syntax_institut.androidabschlussprojekt.data.local.entity.MeditationEntity
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem

fun MeditationEntity.toDomain() = MeditationItem(title, imageResId, audioFile, duration)

fun MeditationItem.toEntity() = MeditationEntity(audioFile, title, imageResId, duration)