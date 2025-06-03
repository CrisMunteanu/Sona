package de.syntax_institut.androidabschlussprojekt.presentation.screens.yogaradio

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class YogaRadioViewModel : ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var mediaPlayer: MediaPlayer? = null

    fun togglePlayback() {
        if (_isPlaying.value) {
            stopRadio()
        } else {
            startRadio()
        }
    }

    private fun startRadio() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource("https://stream.rpr1.de/yoga/mp3-128/stream.radiobob.de/")
            setOnPreparedListener {
                it.start()
                _isPlaying.value = true
            }
            setOnErrorListener { _, _, _ ->
                _isPlaying.value = false
                true
            }
            prepareAsync()
        }
    }

    private fun stopRadio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopRadio()
    }
}