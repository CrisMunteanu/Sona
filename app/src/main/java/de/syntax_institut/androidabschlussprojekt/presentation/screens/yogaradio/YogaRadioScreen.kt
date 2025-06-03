package de.syntax_institut.androidabschlussprojekt.presentation.screens.yogaradio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite
import androidx.compose.material3.Icon
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun YogaRadioScreen() {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer() }

    // Direkter MP3-Stream von SomaFM-Drone Zone(ambient/Meditativ)
    val streamUrl = "https://ice4.somafm.com/dronezone-128-mp3"

    // Initialisiere den MediaPlayer beim Start
    LaunchedEffect(Unit) {
        try {
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            mediaPlayer.setDataSource(streamUrl)
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            Log.e("YogaRadio", "Fehler beim Initialisieren: ${e.message}")
        }
    }

    // Medienwiedergabe-Logik
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Radio,
            contentDescription = "Yoga Radio",
            modifier = Modifier.size(80.dp),
            tint = ElegantRed
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isPlaying) "Yoga-Radio läuft 🎵" else "Bereit zum Abspielen",
            style = MaterialTheme.typography.headlineSmall,
            color = NobleBlack
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                try {
                    if (isPlaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.start()
                    }
                    isPlaying = !isPlaying
                } catch (e: Exception) {
                    Toast.makeText(context, "Fehler beim Abspielen", Toast.LENGTH_SHORT).show()
                    Log.e("YogaRadio", "Fehler: ${e.message}")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
        ) {
            Text(
                text = if (isPlaying) "Stoppen" else "Abspielen",
                color = VintageWhite
            )
        }
    }
}