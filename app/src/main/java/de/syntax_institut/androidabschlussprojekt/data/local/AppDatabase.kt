package de.syntax_institut.androidabschlussprojekt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.syntax_institut.androidabschlussprojekt.data.local.dao.FavoriteDao
import de.syntax_institut.androidabschlussprojekt.data.local.dao.JournalDao
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteEntity
import de.syntax_institut.androidabschlussprojekt.data.local.entity.JournalEntry

@Database(
    entities = [FavoriteEntity::class, JournalEntry::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun journalDao(): JournalDao
}