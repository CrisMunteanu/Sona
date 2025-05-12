package de.syntax_institut.androidabschlussprojekt.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meditations")
data class MeditationEntity(
    @PrimaryKey val audioFile: String,
    val title: String,
    val imageResId: Int,
    val duration: String
)