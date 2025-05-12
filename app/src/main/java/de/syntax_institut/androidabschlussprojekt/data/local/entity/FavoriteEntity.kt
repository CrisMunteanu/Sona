package de.syntax_institut.androidabschlussprojekt.data.local.entity



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val audioFile: String, // eindeutig
    val title: String,
    val imageResId: Int,
    val duration: String
)