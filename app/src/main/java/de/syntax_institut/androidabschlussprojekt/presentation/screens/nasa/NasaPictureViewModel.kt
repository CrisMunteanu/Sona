package de.syntax_institut.androidabschlussprojekt.presentation.screens.nasa



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.NasaRepository
import de.syntax_institut.androidabschlussprojekt.domain.model.NasaPicture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NasaPictureViewModel(
    private val repository: NasaRepository
) : ViewModel() {

    private val _nasaPicture = MutableStateFlow<NasaPicture?>(null)
    val nasaPicture: StateFlow<NasaPicture?> = _nasaPicture.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadPictureOfTheDay()
    }

    fun loadPictureOfTheDay() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val picture = repository.getPictureOfTheDay()
                _nasaPicture.value = picture
            } catch (e: Exception) {
                _errorMessage.value = "Fehler beim Laden: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}