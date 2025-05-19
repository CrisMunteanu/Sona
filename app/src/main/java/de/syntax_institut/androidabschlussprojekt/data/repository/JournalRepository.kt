package de.syntax_institut.androidabschlussprojekt.data.repository



import de.syntax_institut.androidabschlussprojekt.data.local.dao.JournalDao
import de.syntax_institut.androidabschlussprojekt.data.local.entity.JournalEntry
import kotlinx.coroutines.flow.Flow

class JournalRepository(
    private val journalDao: JournalDao
) {
    fun getAllEntries(): Flow<List<JournalEntry>> {
        return journalDao.getAllEntries()
    }

    suspend fun addEntry(entry: JournalEntry) {
        journalDao.insertEntry(entry)
    }

    suspend fun deleteEntry(entry: JournalEntry) {
        journalDao.deleteEntry(entry)
    }

    suspend fun clearAll() {
        journalDao.clearAll()
    }
}