package de.syntax_institut.androidabschlussprojekt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.syntax_institut.androidabschlussprojekt.data.local.dao.FavoriteDao
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}