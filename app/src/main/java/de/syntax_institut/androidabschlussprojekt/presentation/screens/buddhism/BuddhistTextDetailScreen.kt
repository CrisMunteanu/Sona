package de.syntax_institut.androidabschlussprojekt.presentation.screens.buddhism

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.data.repository.BuddhistTextRepository
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddhistTextDetailScreen(
    textId: String,
    navController: NavController
) {
    val context = LocalContext.current
    var tts: TextToSpeech? = remember { null }

    val currentLanguage = remember { BuddhistTextViewModel().currentLanguage.value }
    val text = BuddhistTextRepository
        .getTexts(currentLanguage)
        .firstOrNull { it.id == textId }

    val flag = when (currentLanguage) {
        "de" -> "🇩🇪"
        "en" -> "🇬🇧"
        "fr" -> "🇫🇷"
        "es" -> "🇪🇸"
        else -> "🌐"
    }

    DisposableEffect(Unit) {
        tts = TextToSpeech(context) {}
        onDispose { tts?.shutdown() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "$flag ${text?.title ?: "Gebet"}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Zurück",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        text?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = it.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val locale = when (it.languageCode) {
                            "de" -> Locale.GERMAN
                            "en" -> Locale.ENGLISH
                            "fr" -> Locale.FRENCH
                            "es" -> Locale("es")
                            else -> Locale.getDefault()
                        }
                        tts?.language = locale
                        tts?.speak(it.content, TextToSpeech.QUEUE_FLUSH, null, null)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Vorlesen",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Vorlesen", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        } ?: Text(
            "❗ Gebet nicht gefunden.",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.error
        )
    }
}