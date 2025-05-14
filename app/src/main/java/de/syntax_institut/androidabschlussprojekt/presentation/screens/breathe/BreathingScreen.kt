package de.syntax_institut.androidabschlussprojekt.presentation.screens.breathe

import android.media.MediaPlayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
fun BreathingScreen() {
    val context = LocalContext.current

    var phase by remember { mutableStateOf("Einatmen") }
    var scale by remember { mutableStateOf(1f) }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 4000),
        label = "breathingAnimation"
    )

    // MediaPlayer Instanzen (einmalig)
    val inhalePlayer = remember {
        MediaPlayer.create(context, R.raw.inhale_sound).apply { isLooping = false }
    }
    val exhalePlayer = remember {
        MediaPlayer.create(context, R.raw.exhale_sound).apply { isLooping = false }
    }

    // Atemanimation + Sound abspielen
    LaunchedEffect(Unit) {
        while (isActive) {
            phase = context.getString(R.string.breathe_in)
            scale = 1.5f
            inhalePlayer.start()
            delay(4000)

            phase = context.getString(R.string.hold_breath)
            delay(4000)

            phase = context.getString(R.string.breathe_out)
            scale = 1f
            exhalePlayer.start()
            delay(4000)
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BreathingCircleWithLogo(
            animatedScale = animatedScale,
            logoRes = R.drawable.logo_sona
        )

        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = phase,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
            color = ElegantRed
        )
    }
}