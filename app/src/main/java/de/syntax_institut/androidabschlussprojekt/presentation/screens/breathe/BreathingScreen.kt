package de.syntax_institut.androidabschlussprojekt.presentation.screens.breathe

import android.media.MediaPlayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.components.BreathingCircleWithLogo
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreathingScreen() {
    val context = LocalContext.current

    var isRunning by remember { mutableStateOf(false) }
    var phase by remember { mutableStateOf(context.getString(R.string.breathe_in)) }
    var scale by remember { mutableStateOf(1f) }
    var timeElapsed by remember { mutableStateOf(0) }
    var soundEnabled by remember { mutableStateOf(true) }

    // Auswahlbare Dauer (in Sekunden)
    val durationOptions = listOf(60, 180, 300)
    var selectedDuration by remember { mutableStateOf(durationOptions[0]) }
    var expanded by remember { mutableStateOf(false) }

    val progress = timeElapsed / selectedDuration.toFloat()

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(4000),
        label = "breathingAnimation"
    )

    val inhalePlayer = remember { MediaPlayer.create(context, R.raw.inhale_sound) }
    val exhalePlayer = remember { MediaPlayer.create(context, R.raw.exhale_sound) }

    //  Ablauf
    LaunchedEffect(isRunning) {
        while (isRunning && isActive && timeElapsed < selectedDuration) {
            phase = context.getString(R.string.breathe_in)
            scale = 1.5f
            if (soundEnabled) inhalePlayer.start()
            delay(4000)
            timeElapsed += 4

            phase = context.getString(R.string.hold_breath)
            delay(4000)
            timeElapsed += 4

            phase = context.getString(R.string.breathe_out)
            scale = 1f
            if (soundEnabled) exhalePlayer.start()
            delay(4000)
            timeElapsed += 4
        }
        isRunning = false
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animation
        BreathingCircleWithLogo(
            animatedScale = animatedScale,
            logoRes = R.drawable.logo_sona
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = phase,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
            color = ElegantRed
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fortschritt
        LinearProgressIndicator(
            progress = progress.coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${timeElapsed}s / ${selectedDuration}s",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Dauer-Auswahl
        if (!isRunning) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = "${selectedDuration / 60} min",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Dauer") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    durationOptions.forEach { duration ->
                        DropdownMenuItem(
                            text = { Text("${duration / 60} Minuten") },
                            onClick = {
                                selectedDuration = duration
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Start / Stop + Ton
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    isRunning = !isRunning
                    if (!isRunning) {
                        timeElapsed = 0
                        phase = context.getString(R.string.breathe_in)
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isRunning) context.getString(R.string.stop) else context.getString(R.string.start))
            }

            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                onClick = { soundEnabled = !soundEnabled },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (soundEnabled) "🔊 Ton an" else "🔇 Ton aus")
            }
        }
    }
}