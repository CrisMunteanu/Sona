package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteQuoteEntity
import de.syntax_institut.androidabschlussprojekt.data.repository.FavoriteQuoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteQuoteViewModel(
    private val repository: FavoriteQuoteRepository
) : ViewModel() {

    // Direkte Bindung an Repository Flow (statt Zwischen-Cache)
    val favoriteQuotes: StateFlow<List<FavoriteQuoteEntity>> =
        repository.favoriteQuotes
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun addFavorite(quote: FavoriteQuoteEntity) {
        viewModelScope.launch {
            repository.addFavorite(quote)
        }
    }

    fun removeFavorite(quote: FavoriteQuoteEntity) {
        viewModelScope.launch {
            repository.removeFavorite(quote)
        }
    }

    fun isFavorite(quote: FavoriteQuoteEntity): Boolean {
        return favoriteQuotes.value.any { it.text == quote.text && it.author == quote.author }
    }

    fun removeQuoteByTextAndAuthor(text: String, author: String) {
        favoriteQuotes.value.find { it.text == text && it.author == author }?.let {
            removeFavorite(it)
        }
    }

    fun clearAllFavorites() {
        viewModelScope.launch {
            repository.clearAllFavorites()
        }
    }
}