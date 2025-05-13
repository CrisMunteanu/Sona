package de.syntax_institut.androidabschlussprojekt.presentation.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import de.syntax_institut.androidabschlussprojekt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryStartScreen(
    onItemSelected: (MeditationItem) -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Wähle eine Kategorie",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 28.dp)
        )

        viewModel.allItems.forEach { item ->
            Button(
                onClick = { onItemSelected(item) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(text = item.title)
            }
        }


        Spacer(modifier = Modifier.weight(2f))
        Image(
            painter = painterResource(id = R.drawable.placeholder_image),
            contentDescription = "Startbild",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        )
    }
}