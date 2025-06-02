package de.syntax_institut.androidabschlussprojekt.presentation.screens.cosmic



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.NasaRepository
import de.syntax_institut.androidabschlussprojekt.domain.model.CosmicMeditationApod
import de.syntax_institut.androidabschlussprojekt.domain.util.toCosmicMeditationApod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CosmicMeditationViewModel(
    private val nasaRepository: NasaRepository
) : ViewModel() {

    private val _cosmicMeditation = MutableStateFlow<CosmicMeditationApod?>(null)
    val cosmicMeditation: StateFlow<CosmicMeditationApod?> = _cosmicMeditation.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadCosmicMeditation()
    }

    fun loadCosmicMeditation() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val nasaPicture = nasaRepository.getPictureOfTheDay()
                _cosmicMeditation.value = nasaPicture.toCosmicMeditationApod()
            } catch (e: Exception) {
                _error.value = "Fehler beim Laden: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}