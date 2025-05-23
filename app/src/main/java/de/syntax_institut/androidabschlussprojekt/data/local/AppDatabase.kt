package de.syntax_institut.androidabschlussprojekt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.syntax_institut.androidabschlussprojekt.data.local.dao.FavoriteDao
import de.syntax_institut.androidabschlussprojekt.data.local.dao.FavoriteQuoteDao
import de.syntax_institut.androidabschlussprojekt.data.local.dao.JournalDao
import de.syntax_institut.androidabschlussprojekt.data.local.dao.MeditationHistoryDao
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteEntity
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteQuoteEntity
import de.syntax_institut.androidabschlussprojekt.data.local.entity.JournalEntry
import de.syntax_institut.androidabschlussprojekt.data.local.entity.MeditationHistoryEntity

@Database(
    entities = [
        FavoriteEntity::class,
        JournalEntry::class,
        FavoriteQuoteEntity::class,
        MeditationHistoryEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun journalDao(): JournalDao
    abstract fun favoriteQuoteDao(): FavoriteQuoteDao
    abstract fun meditationHistoryDao(): MeditationHistoryDao
}