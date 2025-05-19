package de.syntax_institut.androidabschlussprojekt.data.local.dao

import androidx.room.*
import de.syntax_institut.androidabschlussprojekt.data.local.entity.JournalEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntry)

    @Delete
    suspend fun deleteEntry(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>

    @Query("DELETE FROM journal_entries")
    suspend fun clearAll()
}