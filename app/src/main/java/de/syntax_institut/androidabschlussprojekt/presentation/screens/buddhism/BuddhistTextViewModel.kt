package de.syntax_institut.androidabschlussprojekt.presentation.screens.buddhism



import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import de.syntax_institut.androidabschlussprojekt.data.repository.BuddhistTextRepository
import de.syntax_institut.androidabschlussprojekt.domain.model.BuddhistText

class BuddhistTextViewModel : ViewModel() {

    // Aktuelle Sprache
    private val _currentLanguage = MutableStateFlow("de")
    val currentLanguage: StateFlow<String> = _currentLanguage

    // Liste aller Gebete (abhängig von Sprache)
    private val _buddhistTexts = MutableStateFlow<List<BuddhistText>>(emptyList())
    val buddhistTexts: StateFlow<List<BuddhistText>> = _buddhistTexts

    init {
        loadTextsForLanguage("de")
    }

    fun loadTextsForLanguage(languageCode: String) {
        _currentLanguage.value = languageCode
        _buddhistTexts.value = BuddhistTextRepository.getTexts(languageCode)
    }
}