package de.syntax_institut.androidabschlussprojekt.presentation.screens.buddhism

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.androidabschlussprojekt.domain.model.BuddhistText

@Composable
fun BuddhistTextsScreen(
    navController: NavController,
    viewModel: BuddhistTextViewModel = viewModel()
) {
    val languageCode by viewModel.currentLanguage.collectAsState()
    val texts by viewModel.buddhistTexts.collectAsState()

    val languageOptions = listOf(
        "de" to "🇩🇪 Deutsch",
        "en" to "🇬🇧 English",
        "fr" to "🇫🇷 Français",
        "es" to "🇪🇸 Español"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "🌐 Sprache wählen",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                val selected = languageOptions.firstOrNull { it.first == languageCode }?.second ?: languageCode
                Text(text = selected, color = MaterialTheme.colorScheme.onBackground)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                languageOptions.forEach { (code, label) ->
                    DropdownMenuItem(
                        text = { Text(label, color = MaterialTheme.colorScheme.onBackground) },
                        onClick = {
                            viewModel.loadTextsForLanguage(code)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(texts) { text ->
                BuddhistTextItem(text = text) {
                    navController.navigate("buddhist_detail/${text.id}")
                }
                Divider(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun BuddhistTextItem(text: BuddhistText, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = text.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text.content.take(80) + "...",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}