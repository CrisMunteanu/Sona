package de.syntax_institut.androidabschlussprojekt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meditation_history")
data class MeditationHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val fileName: String,
    val timestamp: Long,
    val duration: Int
)