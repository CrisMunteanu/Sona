package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.entity.MeditationHistoryEntity
import de.syntax_institut.androidabschlussprojekt.data.repository.MeditationHistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MeditationHistoryViewModel(
    private val repository: MeditationHistoryRepository
) : ViewModel() {

    val history: StateFlow<List<MeditationHistoryEntity>> =
        repository.allMeditations.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun saveMeditation(title: String, fileName: String, duration: Int) {
        val entry = MeditationHistoryEntity(
            title = title,
            fileName = fileName,
            timestamp = System.currentTimeMillis(),
            duration = duration
        )
        viewModelScope.launch { repository.insert(entry) }
    }


    fun clearHistory() {
        viewModelScope.launch { repository.clear() }
    }


    fun deleteHistoryItem(item: MeditationHistoryEntity) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}