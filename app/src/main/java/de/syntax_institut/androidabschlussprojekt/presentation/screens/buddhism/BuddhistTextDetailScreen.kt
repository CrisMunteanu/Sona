package de.syntax_institut.androidabschlussprojekt.presentation.screens.buddhism

import android.speech.tts.TextToSpeech
import android.widget.Toast
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
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddhistTextDetailScreen(
    textId: String,
    navController: NavController,
    viewModel: BuddhistTextViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val texts by viewModel.buddhistTexts.collectAsState()
    val text = texts.firstOrNull { it.id == textId }

    var tts: TextToSpeech? by remember { mutableStateOf(null) }

    DisposableEffect(context) {
        val ttsInstance = TextToSpeech(context) {}
        tts = ttsInstance
        onDispose { ttsInstance.shutdown() }
    }

    val flag = when (currentLanguage) {
        "de" -> "🇩🇪"
        "en" -> "🇬🇧"
        "fr" -> "🇫🇷"
        "es" -> "🇪🇸"
        else -> "🌐"
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
        text?.let { prayer ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = prayer.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val locale = when (prayer.languageCode) {
                            "de" -> Locale("de", "DE")
                            "en" -> Locale("en", "US")
                            "fr" -> Locale("fr", "FR")
                            "es" -> Locale("es", "ES")
                            else -> Locale.getDefault()
                        }

                        val result = tts?.setLanguage(locale)
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(
                                context,
                                "❗ Sprache wird nicht unterstützt.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            tts?.speak(prayer.content, TextToSpeech.QUEUE_FLUSH, null, null)
                        }
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
            text = "❗ Gebet nicht gefunden.",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.error
        )
    }
}