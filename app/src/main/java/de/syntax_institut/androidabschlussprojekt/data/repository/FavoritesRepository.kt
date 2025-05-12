package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.dao.FavoriteDao
import de.syntax_institut.androidabschlussprojekt.data.local.mapper.toFavoriteEntity
import de.syntax_institut.androidabschlussprojekt.data.local.mapper.toMeditationItem
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(
    private val dao: FavoriteDao
) {
    val favorites: Flow<List<MeditationItem>> = dao.getAllFavorites().map { list ->
        list.map { it.toMeditationItem() }
    }

    suspend fun toggleFavorite(item: MeditationItem) {
        if (dao.isFavorite(item.audioFile)) {
            dao.deleteFavorite(item.toFavoriteEntity())
        } else {
            dao.insertFavorite(item.toFavoriteEntity())
        }
    }

    suspend fun isFavorite(item: MeditationItem): Boolean {
        return dao.isFavorite(item.audioFile)
    }
}