package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.PixabayMusicRepository
import de.syntax_institut.androidabschlussprojekt.domain.model.PixabayMusicTrack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import de.syntax_institut.androidabschlussprojekt.data.remote.dto.toPixabayTrack

class PixabayMusicViewModel(
    private val repository: PixabayMusicRepository
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<PixabayMusicTrack>>(emptyList())
    val tracks: StateFlow<List<PixabayMusicTrack>> = _tracks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var currentPage = 1

    init {
        loadTracks()
    }

    fun loadTracks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val rawItems = repository.getMeditationTracks(currentPage)
                _tracks.value = rawItems.map { it.toPixabayTrack() }
                Log.d("PixabayMusicVM", "Anzahl geladener Tracks: ${_tracks.value.size}")
            } catch (e: Exception) {
                Log.e("PixabayMusicVM", "Fehler beim Laden: ${e.message}", e)
            }
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        currentPage++
        loadTracks()
    }

    fun loadPreviousPage() {
        if (currentPage > 1) {
            currentPage--
            loadTracks()
        }
    }

    fun canGoBack(): Boolean = currentPage > 1
}