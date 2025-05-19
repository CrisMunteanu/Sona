package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.repository.FavoritesRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.QuoteRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ZenQuotesRepository
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.domain.model.Quote
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val quoteRepository: QuoteRepository,             // für type.fit (Gallery)
    private val zenQuotesRepository: ZenQuotesRepository      // für ZenQuotes (Player)
) : ViewModel() {

    val allItems = listOf(
        MeditationItem("Sleep", R.drawable.sleep1, "sleep_peaceful1.mp3", "08:30"),
        MeditationItem("Focus", R.drawable.focus1, "focus_deep1.mp3", "07:20"),
        MeditationItem("Breathe", R.drawable.breathe1, "breathe_mindful1.mp3", "05:45"),
        MeditationItem("Morning", R.drawable.morning1, "morning_piano1.mp3", "06:10")
    )

    val favorites: StateFlow<List<MeditationItem>> = favoritesRepository.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _playerQuote = MutableStateFlow<Quote?>(null)
    val playerQuote: StateFlow<Quote?> = _playerQuote.asStateFlow()

    fun loadPlayerQuote() {
        viewModelScope.launch {
            try {
                _playerQuote.value = zenQuotesRepository.getRandomQuote()
            } catch (e: Exception) {
                _playerQuote.value = Quote("Keep calm and breathe.", "Sona")
            }
        }
    }

    fun reloadPlayerQuote() {
        viewModelScope.launch {
            _playerQuote.value = null
            delay(200)
            loadPlayerQuote()
        }
    }

    // ---------- type.fit Zitate für QuoteGallery ----------
    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()

    fun loadAllQuotes() {
        viewModelScope.launch {
            try {
                _quotes.value = quoteRepository.getAllQuotes()
            } catch (e: Exception) {
                _quotes.value = emptyList()
            }
        }
    }

    // ---------- Kategorie-Zitat ----------
    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote.asStateFlow()

    fun loadQuoteForCategory(category: String) {
        viewModelScope.launch {
            try {
                _quote.value = quoteRepository.getQuoteForCategory(category)
            } catch (e: Exception) {
                _quote.value = null
            }
        }
    }

    // ---------- Tägliches Zufallszitat ----------
    private val _dailyQuote = MutableStateFlow<Quote?>(null)
    val dailyQuote: StateFlow<Quote?> = _dailyQuote.asStateFlow()

    fun loadDailyQuote() {
        viewModelScope.launch {
            try {
                _dailyQuote.value = quoteRepository.getRandomQuote()
            } catch (e: Exception) {
                _dailyQuote.value = Quote("Heute ist ein guter Tag für Achtsamkeit.", "Sona")
            }
        }
    }

    // ---------- Favoriten ----------
    fun toggleFavorite(item: MeditationItem) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(item)
        }
    }

    fun isFavorite(item: MeditationItem): Boolean {
        return favorites.value.any { it.audioFile == item.audioFile }
    }
}