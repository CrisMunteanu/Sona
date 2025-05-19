package de.syntax_institut.androidabschlussprojekt.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.presentation.theme.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel

@Composable
fun TodaySuggestionCard(
    viewModel: MainViewModel,
    onItemSelected: (MeditationItem) -> Unit
) {
    val quote by viewModel.dailyQuote.collectAsState()
    val randomMeditation = remember { viewModel.allItems.randomOrNull() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = VintageWhite),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🧘 Heute für dich", fontSize = 20.sp, color = ElegantRed)

            Spacer(modifier = Modifier.height(8.dp))

            quote?.let {
                Text(
                    text = "„${it.text}“",
                    color = SoftPurple,
                    fontSize = 16.sp
                )
                Text(
                    text = "- ${it.author ?: "Unbekannt"}",
                    fontSize = 14.sp,
                    color = NobleBlack,
                    modifier = Modifier.padding(top = 4.dp)
                )
            } ?: Text("Lade Zitat...", color = SoftPurple)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    randomMeditation?.let { onItemSelected(it) }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
            ) {
                Text("Empfohlene Meditation starten", color = VintageWhite)
            }
        }
    }
}