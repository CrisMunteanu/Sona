package de.syntax_institut.androidabschlussprojekt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.entity.JournalEntry
import de.syntax_institut.androidabschlussprojekt.data.repository.JournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JournalViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    val entries: StateFlow<List<JournalEntry>> = _entries.asStateFlow()

    init {
        loadEntries()
    }

    private fun loadEntries() {
        viewModelScope.launch {
            journalRepository.getAllEntries().collect {
                _entries.value = it
            }
        }
    }

    fun addEntry(text: String) {
        val entry = JournalEntry(
            timestamp = System.currentTimeMillis(),
            content = text
        )
        viewModelScope.launch {
            journalRepository.addEntry(entry)
        }
    }

    fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch {
            journalRepository.deleteEntry(entry)
        }
    }
}