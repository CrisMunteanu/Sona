package de.syntax_institut.androidabschlussprojekt.presentation.screens.player

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.domain.util.formatTime
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun AudioPlayerScreen(
    fileName: String,
    imageResId: Int
) {
    val context = LocalContext.current
    val viewModel: MainViewModel = koinViewModel()

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var duration by remember { mutableIntStateOf(0) }
    var currentPosition by remember { mutableIntStateOf(0) }
    var userSeeking by remember { mutableStateOf(false) }

    val title = fileName.removeSuffix(".mp3")
        .replace("_", " ")
        .replaceFirstChar { it.uppercase() }

    val currentItem = MeditationItem(title, imageResId, fileName, "")

    // Favoritenstatus beobachten
    val favorites by viewModel.favorites.collectAsState()
    val isFavorite = favorites.any { it.audioFile == currentItem.audioFile }

    // Zitat aus ZenQuotes laden
    val quote by viewModel.playerQuote.collectAsState()

    // Bei Audio-Wechsel: Zitat neu laden
    LaunchedEffect(fileName) {
        viewModel.loadPlayerQuote()
    }

    // MediaPlayer initialisieren
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
        onDispose { player.release() }
    }

    // Fortschritt aktualisieren
    LaunchedEffect(isPlaying) {
        while (isPlaying && !userSeeking) {
            delay(500L)
            mediaPlayer?.let { currentPosition = it.currentPosition }
        }
    }

    // Funktion zum Öffnen der Wikipedia-Seite
    val openAuthorWiki: (String) -> Unit = { author ->
        val wikiUrl = "https://en.wikipedia.org/wiki/${author.replace(" ", "_")}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl))
        context.startActivity(intent)
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = currentItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(240.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(1.dp, SoftPurple, CircleShape)
                .padding(2.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Titel + Favorit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = SoftPurple
            )

            // Herz mit Farb- und Scale-Animation

            val targetColor = if (isFavorite) ElegantRed else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            val animatedColor by animateColorAsState(targetColor)

            val animatedScale by animateFloatAsState(
                targetValue = if (isFavorite) 1.2f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            IconButton(
                onClick = { viewModel.toggleFavorite(currentItem) },
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = animatedColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fortschrittsbalken
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

        // Play/Pause Button
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

        // Zitat-Anzeige mit Wikipedia-Intent
        quote?.let {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "“${it.text}”",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftPurple,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (it.author.isNotBlank()) {
                    Text(
                        text = "- ${it.author}",
                        style = MaterialTheme.typography.labelMedium,
                        color = ElegantRed,
                        modifier = Modifier
                            .clickable { openAuthorWiki(it.author) }
                            .padding(4.dp)
                    )
                } else {
                    Text(
                        text = "- Unknown",
                        style = MaterialTheme.typography.labelMedium,
                        color = ElegantRed
                    )
                }
            }
        } ?: Text(
            text = "Lade Zitat...",
            style = MaterialTheme.typography.labelLarge,
            color = SoftPurple
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Logo
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sona),
                contentDescription = "Sona Logo",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )
        }
    }
}