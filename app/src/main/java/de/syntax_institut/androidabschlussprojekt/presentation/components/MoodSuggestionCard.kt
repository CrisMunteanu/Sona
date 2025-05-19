package de.syntax_institut.androidabschlussprojekt.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.FlowRow


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodSuggestionCard(
    navController: NavController,
    viewModel: MainViewModel
) {
    val moods = listOf(
        "😌 Entspannt" to "Sleep",
        "😣 Gestresst" to "Breathe",
        "😴 Müde" to "Morning",
        "😕 Unfokussiert" to "Focus"
    )

    var isExpanded by remember { mutableStateOf(false) }
    var selectedMood by remember { mutableStateOf<String?>(null) }

    val suggestion = selectedMood?.let { mood ->
        viewModel.allItems.find { it.title.equals(moods.find { it.first == mood }?.second, ignoreCase = true) }
    }

    val quote by viewModel.quote.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = VintageWhite),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Überschrift klickbar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Icon(
                    imageVector = Icons.Default.Mood,
                    contentDescription = "Mood Icon",
                    tint = ElegantRed,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Wie fühlst du dich heute?",
                    fontSize = 20.sp,
                    color = NobleBlack
                )
            }

            // Nur wenn geklickt → Stimmungsauswahl anzeigen
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        moods.forEach { (emojiText, _) ->
                            val isSelected = selectedMood == emojiText
                            val backgroundColor by animateColorAsState(
                                targetValue = if (isSelected) ElegantRed else MaterialTheme.colorScheme.surface,
                                animationSpec = tween(durationMillis = 300)
                            )
                            val textColor by animateColorAsState(
                                targetValue = if (isSelected) VintageWhite else ElegantRed,
                                animationSpec = tween(durationMillis = 300)
                            )

                            AssistChip(
                                onClick = {
                                    selectedMood = emojiText
                                    val category = moods.find { it.first == emojiText }?.second ?: "Sleep"
                                    viewModel.loadQuoteForCategory(category)
                                },
                                label = { Text(emojiText, color = textColor) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = backgroundColor,
                                    labelColor = textColor
                                )
                            )
                        }
                    }

                    AnimatedVisibility(visible = suggestion != null && quote != null) {
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            Text(
                                text = "✨ Empfehlung: ${suggestion?.title}",
                                fontSize = 18.sp,
                                color = ElegantRed
                            )
                            Text(
                                text = "Zitat: \"${quote?.text}\" - ${quote?.author ?: "Unbekannt"}",
                                fontSize = 14.sp,
                                color = NobleBlack,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    suggestion?.let {
                                        navController.navigate("player/${it.audioFile}/${it.imageResId}")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
                            ) {
                                Text("Jetzt anhören")
                            }
                        }
                    }
                }
            }
        }
    }
}