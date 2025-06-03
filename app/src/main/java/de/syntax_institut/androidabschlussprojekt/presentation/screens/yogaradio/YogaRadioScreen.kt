package de.syntax_institut.androidabschlussprojekt.presentation.screens.yogaradio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.koinViewModel


@Composable
fun YogaRadioScreen(viewModel: YogaRadioViewModel = koinViewModel()) {
    val context = LocalContext.current
    val isPlaying by viewModel.isPlaying.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Radio,
            contentDescription = "Yoga Radio",
            modifier = Modifier.size(80.dp),
            tint = ElegantRed
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isPlaying) "Yoga-Radio läuft 🎵" else "Bereit zum Abspielen",
            style = MaterialTheme.typography.headlineSmall,
            color = NobleBlack
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.togglePlayback(context) },
            colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
        ) {
            Text(
                text = if (isPlaying) "Stoppen" else "Abspielen",
                color = VintageWhite
            )
        }
    }
}