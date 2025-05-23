package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteQuoteEntity
import de.syntax_institut.androidabschlussprojekt.data.repository.FavoriteQuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteQuoteViewModel(
    private val repository: FavoriteQuoteRepository
) : ViewModel() {

    private val _favoriteQuotes = MutableStateFlow<List<FavoriteQuoteEntity>>(emptyList())
    val favoriteQuotes: StateFlow<List<FavoriteQuoteEntity>> = _favoriteQuotes.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.favoriteQuotes.collect { quotes ->
                _favoriteQuotes.value = quotes
            }
        }
    }

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

    //  Wrapper für Quote-Modell (wenn benötigt für UI)
    fun removeQuoteByTextAndAuthor(text: String, author: String) {
        val quote = favoriteQuotes.value.find { it.text == text && it.author == author }
        quote?.let {
            removeFavorite(it)
        }
    }
}