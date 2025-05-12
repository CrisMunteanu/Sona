package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.dao.MeditationDao
import de.syntax_institut.androidabschlussprojekt.data.local.mapper.toDomain
import de.syntax_institut.androidabschlussprojekt.data.local.mapper.toEntity
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MeditationRepository(
    private val dao: MeditationDao
) {

    fun getFavorites(): Flow<List<MeditationItem>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun addToFavorites(item: MeditationItem) {
        dao.insert(item.toEntity())
    }

    suspend fun removeFromFavorites(item: MeditationItem) {
        dao.delete(item.toEntity())
    }

    suspend fun toggleFavorite(item: MeditationItem) {
        val currentFavorites = dao.getAllOnce()
        val isFavorite = currentFavorites.any { it.audioFile == item.audioFile }

        if (isFavorite) {
            dao.delete(item.toEntity())
        } else {
            dao.insert(item.toEntity())
        }
    }
}