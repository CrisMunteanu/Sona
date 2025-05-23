package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.dao.FavoriteQuoteDao
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteQuoteEntity
import kotlinx.coroutines.flow.Flow

class FavoriteQuoteRepository(private val dao: FavoriteQuoteDao) {

    val favoriteQuotes: Flow<List<FavoriteQuoteEntity>> = dao.getAll()

    suspend fun addFavorite(quote: FavoriteQuoteEntity) {
        dao.insert(quote)
    }

    suspend fun removeFavorite(quote: FavoriteQuoteEntity) {
        dao.delete(quote)
    }

    suspend fun isFavorite(text: String, author: String): Boolean {
        return dao.isFavorite(text, author)
    }

    suspend fun clearAllFavorites() {
        dao.deleteAll()
    }
}