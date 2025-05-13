package de.syntax_institut.androidabschlussprojekt.presentation.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.SettingsDataStore
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    var logoVisible by remember { mutableStateOf(false) }

    // Entscheidet nach Splash-Dauer ob Onboarding oder Start
    LaunchedEffect(Unit) {
        logoVisible = true
        delay(2500)

        val hasSeenOnboarding = SettingsDataStore.getOnboardingSeen(context)

        navController.navigate(
            if (hasSeenOnboarding) "start" else "onboarding"
        ) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4E7CD)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo mit animierter Sichtbarkeit
            AnimatedVisibility(
                visible = logoVisible,
                enter = fadeIn(tween(1000)),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier.size(260.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                listOf(
                                    Color(0xFF692621),
                                    Color(0xFF9C5248),
                                    Color(0xFFE29588),
                                    Color(0xFF692621)
                                )
                            ),
                            style = Stroke(width = 12f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_sona),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Zitat einblenden
            AnimatedVisibility(
                visible = logoVisible,
                enter = fadeIn(tween(1500)),
                exit = fadeOut()
            ) {
                Text(
                    text = stringResource(R.string.splash_quote),
                    color = Color(0xFF692621),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 32.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Debug-Hinweis (nur sichtbar in BuildConfig.DEBUG)
            if (BuildConfig.DEBUG) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.reset_onboarding),
                    color = Color.Blue,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}