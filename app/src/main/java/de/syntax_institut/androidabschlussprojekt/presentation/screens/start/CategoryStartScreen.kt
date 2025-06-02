package de.syntax_institut.androidabschlussprojekt.presentation.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.presentation.components.TodaySuggestionCard
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryStartScreen(
    onItemSelected: (MeditationItem) -> Unit,
    viewModel: MainViewModel = koinViewModel(),
    onPixabayClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadDailyQuote()
    }

    val allItems = viewModel.allItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Wähle eine Kategorie",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TodaySuggestionCard(
            viewModel = viewModel,
            onItemSelected = onItemSelected
        )

        Spacer(modifier = Modifier.height(24.dp))

        allItems.forEach { item ->
            Button(
                onClick = { onItemSelected(item) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.title,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = onPixabayClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
        ) {
            Icon(Icons.Default.Cloud, contentDescription = "Online Musik")
            Spacer(modifier = Modifier.width(10.dp))
            Text("🌐 Online-Meditationen")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.placeholder_image),
            contentDescription = "Startbild",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(top = 16.dp)
        )
    }
}