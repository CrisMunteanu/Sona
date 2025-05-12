package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.repository.FavoritesRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.QuoteRepository
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.domain.model.Quote
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    val allItems = listOf(
        MeditationItem("Sleep", R.drawable.sleep1, "sleep_peaceful1.mp3", "08:30"),
        MeditationItem("Focus", R.drawable.focus1, "focus_deep1.mp3", "07:20"),
        MeditationItem("Breathe", R.drawable.breathe1, "breathe_mindful1.mp3", "05:45"),
        MeditationItem("Morning", R.drawable.morning1, "morning_piano1.mp3", "06:10")
    )

    val favorites: StateFlow<List<MeditationItem>> = favoritesRepository.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote.asStateFlow()

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()

    fun toggleFavorite(item: MeditationItem) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(item)
        }
    }

    fun isFavorite(item: MeditationItem): Boolean {
        return favorites.value.any { it.audioFile == item.audioFile }
    }

    fun loadQuoteForCategory(category: String) {
        viewModelScope.launch {
            try {
                _quote.value = quoteRepository.getQuoteForCategory(category)
            } catch (e: Exception) {
                e.printStackTrace()
                _quote.value = null
            }
        }
    }

    fun loadAllQuotes() {
        viewModelScope.launch {
            try {
                _quotes.value = quoteRepository.getAllQuotes()
            } catch (e: Exception) {
                e.printStackTrace()
                _quotes.value = emptyList()
            }
        }
    }
}