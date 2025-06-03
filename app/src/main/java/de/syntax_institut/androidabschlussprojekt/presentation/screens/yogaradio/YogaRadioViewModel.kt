package de.syntax_institut.androidabschlussprojekt.presentation.screens.yogaradio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class YogaRadioViewModel : ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var mediaPlayer: MediaPlayer? = null

    private val streamUrl = "https://ice4.somafm.com/dronezone-128-mp3"

    fun togglePlayback(context: Context) {
        if (_isPlaying.value) {
            stopRadio()
        } else {
            startRadio(context)
        }
    }

    private fun startRadio(context: Context) {
        mediaPlayer = MediaPlayer().apply {
            try {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(streamUrl)
                setOnPreparedListener {
                    it.start()
                    _isPlaying.value = true
                }
                setOnErrorListener { _, _, _ ->
                    Toast.makeText(context, "Fehler beim Abspielen", Toast.LENGTH_SHORT).show()
                    _isPlaying.value = false
                    true
                }
                prepareAsync()
            } catch (e: Exception) {
                Toast.makeText(context, "Fehler beim Initialisieren", Toast.LENGTH_SHORT).show()
                Log.e("YogaRadio", "Fehler beim Start: ${e.message}")
                _isPlaying.value = false
            }
        }
    }

    private fun stopRadio() {
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) {
            Log.e("YogaRadio", "Fehler beim Stoppen: ${e.message}")
        }
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
    }

    override fun onCleared() {
        stopRadio()
        super.onCleared()
    }
}