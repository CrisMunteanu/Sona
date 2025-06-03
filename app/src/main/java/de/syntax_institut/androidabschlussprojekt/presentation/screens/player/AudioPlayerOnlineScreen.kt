package de.syntax_institut.androidabschlussprojekt.presentation.screens.player

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun AudioPlayerOnlineScreen(
    fileName: String,
    imageUrl: String,
    title: String,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: MainViewModel = koinViewModel()

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var duration by remember { mutableIntStateOf(0) }
    var currentPosition by remember { mutableIntStateOf(0) }
    var userSeeking by remember { mutableStateOf(false) }

    val favorites by viewModel.favorites.collectAsState()
    val isFavorite = favorites.any { it.audioFile == fileName }

    val quote by viewModel.playerQuote.collectAsState()

    val animatedColor by animateColorAsState(
        targetValue = if (isFavorite) ElegantRed else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = spring()
    )

    // Lade Zitat
    LaunchedEffect(fileName) {
        viewModel.loadPlayerQuote()
    }

    DisposableEffect(fileName) {
        val player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            val afd = context.assets.openFd(fileName)
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            prepare()
            duration = this.duration
        }
        mediaPlayer = player
        onDispose {
            player.release()
        }
    }

    // Aktualisiere Fortschritt
    LaunchedEffect(isPlaying) {
        while (isPlaying && !userSeeking) {
            delay(500L)
            mediaPlayer?.let { currentPosition = it.currentPosition }
        }
    }

    val openAuthorWiki: (String) -> Unit = { author ->
        val url = "https://en.wikipedia.org/wiki/${author.replace(" ", "_")}"
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(240.dp)
                .clip(CircleShape)
                .border(3.dp, ElegantRed.copy(alpha = 0.4f), CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = SoftPurple
            )
            IconButton(
                onClick = { viewModel.toggleFavoriteByFilename(fileName) },
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer(
                        scaleX = animatedScale,
                        scaleY = animatedScale
                    )
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = animatedColor
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Slider(
            value = currentPosition.toFloat(),
            onValueChange = {
                userSeeking = true
                currentPosition = it.toInt()
            },
            onValueChangeFinished = {
                mediaPlayer?.seekTo(currentPosition)
                userSeeking = false
            },
            valueRange = 0f..(duration.takeIf { it > 0 } ?: 1).toFloat(),
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = ElegantRed,
                activeTrackColor = ElegantRed
            )
        )

        Text(
            text = "${formatTime(currentPosition)} / ${formatTime(duration)}",
            style = MaterialTheme.typography.bodyMedium,
            color = ElegantRed
        )

        Spacer(modifier = Modifier.height(24.dp))

        IconButton(onClick = {
            mediaPlayer?.let {
                if (isPlaying) it.pause() else it.start()
                isPlaying = !isPlaying
            }
        }) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "Play/Pause",
                modifier = Modifier.size(48.dp),
                tint = ElegantRed
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        quote?.let {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "“${it.text}”",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftPurple,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (it.author.isNotBlank()) {
                    Text(
                        text = "- ${it.author}",
                        style = MaterialTheme.typography.labelMedium,
                        color = ElegantRed,
                        modifier = Modifier
                            .clickable { openAuthorWiki(it.author) }
                            .padding(4.dp)
                    )
                }
            }
        } ?: Text(
            text = "Lade Zitat...",
            style = MaterialTheme.typography.labelMedium,
            color = SoftPurple
        )
    }
}

//Hilfsfunktion zur Zeitformatierung
fun formatTime(ms: Int): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(ms.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) % 60
    return String.format("%02d:%02d", minutes, seconds)
}