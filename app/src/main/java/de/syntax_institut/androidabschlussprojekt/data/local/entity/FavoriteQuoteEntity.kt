package de.syntax_institut.androidabschlussprojekt.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_quotes")
data class FavoriteQuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val author: String,
    val authorInfo: String = "",
    val authorImageResId: Int? = null
)