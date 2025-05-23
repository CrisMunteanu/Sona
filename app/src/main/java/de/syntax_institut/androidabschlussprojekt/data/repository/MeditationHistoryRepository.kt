package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.dao.MeditationHistoryDao
import de.syntax_institut.androidabschlussprojekt.data.local.entity.MeditationHistoryEntity
import kotlinx.coroutines.flow.Flow

class MeditationHistoryRepository(
    private val dao: MeditationHistoryDao
) {
    val allMeditations: Flow<List<MeditationHistoryEntity>> = dao.getAll()

    suspend fun insert(history: MeditationHistoryEntity) = dao.insert(history)


    suspend fun clear() = dao.clear()

    suspend fun delete(item: MeditationHistoryEntity) = dao.delete(item)
}