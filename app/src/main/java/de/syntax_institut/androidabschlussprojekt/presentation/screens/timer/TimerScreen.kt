package de.syntax_institut.androidabschlussprojekt.presentation.screens.timer

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    fileName: String,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedMinutes by remember { mutableIntStateOf(5) }
    var sliderValue by remember { mutableFloatStateOf(5f) }
    var isRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableIntStateOf(5 * 60) }

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    // Timer-Logik
    LaunchedEffect(isRunning) {
        while (isRunning && timeLeft > 0) {
            delay(1000)
            timeLeft--
            if (timeLeft == 0) {
                isRunning = false
                mediaPlayer?.pause()
                snackbarHostState.showSnackbar("🧘 Meditation beendet")
            }
        }
    }

    // MediaPlayer vorbereiten
    LaunchedEffect(fileName) {
        val afd = context.assets.openFd(fileName)
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            prepare()
        }
    }

    // Aufräumen bei Verlassen
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meditations-Timer", color = ElegantRed) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = ElegantRed)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = "Timer Icon",
                tint = ElegantRed,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = "🕒 ${timeLeft / 60} Min ${(timeLeft % 60).toString().padStart(2, '0')} Sek",
                style = MaterialTheme.typography.headlineSmall,
                color = SoftPurple
            )

            Text("Dauer auswählen", color = SoftPurple)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(5, 10, 15, 30).forEach { min ->
                    Button(
                        onClick = {
                            selectedMinutes = min
                            sliderValue = min.toFloat()
                            timeLeft = min * 60
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedMinutes == min) ElegantRed else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("$min")
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Feinjustierung: ${sliderValue.toInt()} Min", color = SoftPurple)
                Slider(
                    value = sliderValue,
                    onValueChange = {
                        sliderValue = it
                        selectedMinutes = it.toInt()
                        timeLeft = it.toInt() * 60
                    },
                    valueRange = 1f..60f,
                    steps = 11,
                    colors = SliderDefaults.colors(
                        thumbColor = ElegantRed,
                        activeTrackColor = ElegantRed
                    )
                )
            }

            Button(
                onClick = {
                    if (isRunning) {
                        isRunning = false
                        mediaPlayer?.pause()
                    } else {
                        mediaPlayer?.start()
                        timeLeft = selectedMinutes * 60
                        isRunning = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) Color.Gray else ElegantRed
                )
            ) {
                Text(if (isRunning) "⏹️ Stoppen" else "▶️ Starten")
            }
        }
    }
}